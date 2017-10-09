package com.github.chiby.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.DockerApplicationDefinition;
import com.github.chiby.player.model.LogEntry;
import com.github.chiby.player.model.RunSession;
import com.github.chiby.store.model.repositories.LogEntryRepository;
import com.github.chiby.store.model.repositories.RunSessionRepository;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.AttachParameter;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@NoArgsConstructor
@AllArgsConstructor
public class DockerExecutor {

	@Autowired
	@Setter
	RunSessionRepository runSessionRepository;

	@Autowired
	@Setter
	LogEntryRepository logEntryRepository;

	ExecutorService executor = Executors.newFixedThreadPool(3);

	Map<String, ScheduledExecutorService> stdlogFlushersMap = new HashMap<>();
	Map<String, ScheduledExecutorService> stderrFlushersMap = new HashMap<>();

	public String debug(Application application)
			throws DockerCertificateException, DockerException, InterruptedException {
		DockerClient docker = DefaultDockerClient.fromEnv().build();
		DockerApplicationDefinition dad = (DockerApplicationDefinition) application.getDefinition();

		ContainerConfig config = ContainerConfig.builder().image(dad.getImage()).cmd(dad.getParameters())
				.env(dad.flattenEnvironment()).build();
		ContainerCreation creation = docker.createContainer(config);

		docker.startContainer(creation.id());

		docker.waitContainer(creation.id());
		final String logs;
		try (LogStream stream = docker.logs(creation.id(), LogsParam.stdout(), LogsParam.stderr());) {
			logs = stream.readFully();
		}

		docker.stopContainer(creation.id(), 5);

		docker.removeContainer(creation.id());
		return (logs);

	}

	public String start(Application application, RunSession session)
			throws DockerCertificateException, DockerException, InterruptedException {
		final DockerClient docker = DefaultDockerClient.fromEnv().build();
		DockerApplicationDefinition dad = (DockerApplicationDefinition) application.getDefinition();

		ContainerConfig config = ContainerConfig.builder().image(dad.getImage()).cmd(dad.getParameters())
				.env(dad.flattenEnvironment()).build();
		final ContainerCreation creation = docker.createContainer(config);

		docker.startContainer(creation.id());

		session.setExecutionId(creation.id());
		runSessionRepository.save(session);

		try {
			final PipedInputStream stdout = new PipedInputStream();
			final PipedInputStream stderr = new PipedInputStream();
			final PipedOutputStream stdout_pipe = new PipedOutputStream(stdout);
			final PipedOutputStream stderr_pipe = new PipedOutputStream(stderr);

			executor.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					docker.attachContainer(creation.id(), AttachParameter.LOGS, AttachParameter.STDOUT,
							AttachParameter.STDERR, AttachParameter.STREAM).attach(stdout_pipe, stderr_pipe);
					return null;
				}
			});
			
		    scheduleLogFlusher(session, creation.id(), stdout, stdlogFlushersMap, false);
		    
			scheduleLogFlusher(session, creation.id(), stderr, stderrFlushersMap, true);


		} catch (IOException ioe) {
			log.log(Level.FINE, "Could not attach to container " + creation.id() + " log output", ioe);
		}

		return creation.id();
	}

	private void scheduleLogFlusher(RunSession session, final String creationId,
			final PipedInputStream stdout, Map<String, ScheduledExecutorService> map, boolean stdErr) {
		final BufferedReader readStdout = new BufferedReader(new InputStreamReader(stdout));
		ScheduledExecutorService stdLogFlusher = Executors.newSingleThreadScheduledExecutor();
		map.put(creationId, stdLogFlusher);
		stdLogFlusher.schedule(new Runnable() {
			@Override
			public void run() {
				// Convert output lines to LogEntries
				java.util.List<LogEntry> entries = readStdout.lines()
						.map(line -> LogEntry.builder().line(line).error(stdErr).runSession(session).build())
						.collect(Collectors.toList());
				logEntryRepository.save(entries);
			}
		}, 500, TimeUnit.MILLISECONDS);
	}

	public void stop(RunSession session) throws DockerCertificateException, DockerException, InterruptedException {
		DockerClient docker = DefaultDockerClient.fromEnv().build();

		if (session.getExecutionId() != null) {
			// Double check that the session hasn't yet been marked as stopped
			RunSession sessionEntity = runSessionRepository.findOneByExecutionIdAndRunning(session.getExecutionId(),
					true);
			if (sessionEntity != null) {
				docker.stopContainer(session.getExecutionId(), 3);
				sessionEntity.setRunning(false);
				sessionEntity.setStoppedAt(new Date());
				runSessionRepository.save(sessionEntity);
				docker.removeContainer(session.getExecutionId());
			}
			String executionId = session.getExecutionId();
			// In any case, cancel and remove any left over log flushing executors
			if(stdlogFlushersMap.containsKey(executionId)){
				stdlogFlushersMap.get(executionId).shutdown();
				stdlogFlushersMap.remove(executionId);
			}
			// In any case, cancel and remove any left over stderr flushing executors
			if(stderrFlushersMap.containsKey(executionId)){
				stderrFlushersMap.get(executionId).shutdown();
				stderrFlushersMap.remove(executionId);
			}
		} else {
			log.warning("Cannot stop session " + session.uuid + " : No execution id !");
		}
	}

}
