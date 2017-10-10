package client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.chiby.player.DockerExecutor;
import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.DockerApplicationDefinition;
import com.github.chiby.player.model.LogEntry;
import com.github.chiby.player.model.RunSession;
import com.github.chiby.store.model.repositories.LogEntryRepository;
import com.github.chiby.store.model.repositories.RunSessionRepository;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerInfo;

@RunWith(MockitoJUnitRunner.class)
public class DockerExecutorTest {
	@Mock
	LogEntryRepository logEntryRepository;
	
	@Mock
	RunSessionRepository runSessionRepository;
	
//	@Test
//	public void testSimpleDebug() throws DockerCertificateException, DockerException, InterruptedException {
//		Map<String,String> env = new HashMap<>();
//		String input = "Hello Docker Executor";
//		env.put("VAR1",input);
//		DockerApplicationDefinition daf = DockerApplicationDefinition.builder()
//				        .image("busybox:latest")
//				        .environment(env)
//				        .parameters(Arrays.asList((new String[]{"sh","-c","echo $VAR1"}))).build();
//		Application app =  Application.builder().uuid(UUID.randomUUID())
//				                                .title("My Busybox counter")
//				                                .definition(daf).build();
//		DockerExecutor de = new DockerExecutor();
//		
//		String output = de.debug(app);
//		System.out.println("Output " + output);
//		assertEquals(input, output.trim());
//	}

	/**
	 * Test that the standard output and standard error streams are properly
	 * handled in a multithreaded manner by the docker executor.
	 * @throws Exception
	 */
	@Test
	public void testLogFlushing() throws Exception{
		DockerApplicationDefinition daf = DockerApplicationDefinition.builder()
		        .image("busybox:latest")
		        .parameters(Arrays.asList((new String[]{"sh","-c","echo normal && (>&2 echo error) && echo normal && sleep 1 && echo normal && echo normal "}))).build();
		Application app =  Application.builder().uuid(UUID.randomUUID())
				                                .definition(daf).build();
		DockerExecutor de = new DockerExecutor();
		de.setLogEntryRepository(logEntryRepository);
		de.setRunSessionRepository(runSessionRepository);
		
		RunSession session = new RunSession();
		session.setApplication(app);
		de.start(app, session);
		
		Thread.sleep(500);
		verify(logEntryRepository, times(3)).save((LogEntry)any());
		Thread.sleep(3000);
		
		// In total, we should have saved five lines of output
		verify(logEntryRepository, times(5)).save((LogEntry)any());
	}
	
	@Test
	public void testContainerStopping() throws Exception{
		DockerApplicationDefinition daf = DockerApplicationDefinition.builder()
		        .image("busybox:latest")
		        .parameters(Arrays.asList((new String[]{"sh","-c","echo STARTED && sleep 20 && echo FINISHED"}))).build();
		Application app =  Application.builder().uuid(UUID.randomUUID())
				                                .definition(daf).build();
		
		DockerExecutor de = new DockerExecutor();
		de.setLogEntryRepository(logEntryRepository);
		de.setRunSessionRepository(runSessionRepository);
		
		RunSession session = new RunSession();
		session.setApplication(app);

		de.start(app, session);

		assertTrue(session.getExecutionId() != null);
		when(runSessionRepository.findOneByExecutionIdAndRunning(session.getExecutionId(), true)).thenReturn(session);
		
		// Use a default docker client to check a container was started and is running
		DockerClient docker = DefaultDockerClient.fromEnv().build();
		ContainerInfo ci = docker.inspectContainer(session.getExecutionId());
		assertTrue(ci.state().running());

		Thread.sleep(2000);
		
		de.stop(session, false);
		verify(runSessionRepository, atLeastOnce()).save(session);
		ci = docker.inspectContainer(session.getExecutionId());
		assertFalse(ci.state().running());
		
		docker.removeContainer(session.getExecutionId());
		
	}
}
