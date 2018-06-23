package com.github.chibyhq.playar;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.chibyhq.playar.model.Application;
import com.github.chibyhq.playar.model.ApplicationTypeConstants;
import com.github.chibyhq.playar.model.LogEntry;
import com.github.chibyhq.playar.model.RunSession;
import com.github.chibyhq.store.model.repositories.LogEntryRepository;
import com.github.chibyhq.store.model.repositories.RunSessionRepository;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class PygamezeroExecutor implements IApplicationExecutor {

	@Autowired
	@Setter
	RunSessionRepository runSessionRepository;

	@Autowired
	@Setter
	LogEntryRepository logEntryRepository;

	private Map<String, Process> processesMap = new HashMap<>();
	private Map<String, ExecutorService> executorsMap = new HashMap<>();

	@Override
	public String start(Application application, RunSession session, Path applicationHome) throws Exception {

		ProcessBuilder pBuilder = new ProcessBuilder("pgzrun", ApplicationTypeConstants.PYTHON_APPLICATION_PY);
		pBuilder.directory(applicationHome.toFile());
		if(application.getEnvironment()!=null){
			pBuilder.environment().putAll(application.getEnvironment());
		}

		////////////////////////
		// In Java 8 there is no portable way to obtain the process ID
		// allocating a random unique identifier instead.
		String processUUID = UUID.randomUUID().toString();
		session.setExecutionId(processUUID.toString());
		session.setRunning(true);
		session.setStopped(false);
		runSessionRepository.save(session);
		////////////////////////

		try {
			Process process = pBuilder.start();
			processesMap.put(processUUID, process);
			ExecutorService executor = Executors.newFixedThreadPool(3);
			executorsMap.put(processUUID, executor);
			executor.execute(new ProcessWatcher(process, processUUID));
			executor.execute(new LineGobbler(process.getInputStream(), session, false, processUUID));
			executor.execute(new LineGobbler(process.getErrorStream(), session, true, processUUID));

		} catch (Exception e) {
			log.log(Level.FINE, "Could not attach to process " + processUUID, e);
		}

		return processUUID;
	}

	@Override
	public void stop(RunSession session, Boolean removeContainer) {

		if (session.getExecutionId() != null) {
			String executionId = session.getExecutionId();
			// Double check that the session hasn't yet been marked as stopped
			Process process = processesMap.get(executionId);

			if (process.isAlive()) {
				process.destroyForcibly();
				session.setRunning(false);
				// No need to marked as Stopped, if we made it here it is
				// because the process was actively stopped
				session.setStoppedAt(new Date());
				runSessionRepository.save(session);
			}

			try {
				// In any case, cancel and remove any left over log flushing
				// executors
				if (executorsMap.containsKey(executionId)) {
					executorsMap.get(executionId).shutdown();
				}
			} catch (Exception e) {
				log.warning("Could not interrupt all log flushing schedulers for process " + executionId);
			}
		} else {
			log.info("Cannot stop session " + session.uuid + " : No execution id !");
		}

	}

	@AllArgsConstructor
	class LineGobbler implements Runnable {
		InputStream is;
		RunSession session;
		boolean stdErr;
		String processId;

		public void run() {
			Scanner scanner = new Scanner(is);
			try {
				List<LogEntry> lines = new ArrayList<>();
				while (scanner.hasNext()) {
					String line = scanner.nextLine();
					lines.add(LogEntry.builder().line(line).error(stdErr).build());
				}
				if(session.getLogEntries() == null){
					session.setLogEntries(new ArrayList<>());
				}
				session.getLogEntries().addAll(lines);
				runSessionRepository.save(session);
			} catch (Exception e) {
				log.log(Level.WARNING, "Output interrupted for process " + processId, e);
			} finally {
				scanner.close();
			}
		}
	}

	@AllArgsConstructor
	class ProcessWatcher implements Runnable {
		Process process;
		String executionId;

		public void run() {
			boolean stop = false;
			try {
				while (!stop) {
					Thread.sleep(500);
					if (!process.isAlive()) {
						RunSession session = runSessionRepository.findOneByExecutionIdAndRunning(executionId, true);
						if (session != null) {
							log.finest("Marking process "+executionId+" as 'not running'");
							session.setRunning(false);
							session.setExitCode(process.exitValue());
							session.setStoppedAt(new Date());
							runSessionRepository.save(session);
						}
						stop=true;
					}
				}
			} catch (InterruptedException e) {
				log.warning("Process watcher for " + executionId + " was interrupted");
			}
		}
	}

	public Process getProcessForExecutionId(String executionId) {
		return processesMap.get(executionId);
	}
}
