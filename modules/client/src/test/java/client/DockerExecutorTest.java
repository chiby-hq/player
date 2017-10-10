package client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import static org.mockito.Mockito.*;
import com.github.chiby.player.DockerExecutor;
import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.DockerApplicationDefinition;
import com.github.chiby.player.model.LogEntry;
import com.github.chiby.player.model.RunSession;
import com.github.chiby.store.model.repositories.LogEntryRepository;
import com.github.chiby.store.model.repositories.RunSessionRepository;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

@RunWith(MockitoJUnit44Runner.class)
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
//		Assert.assertEquals(input, output.trim());
//	}

	@SuppressWarnings("unchecked")
	@Test
	public void testLogFlushing() throws Exception{
		DockerApplicationDefinition daf = DockerApplicationDefinition.builder()
		        .image("busybox:latest")
		        .parameters(Arrays.asList((new String[]{"sh","-c","echo normal && (>&2 echo error) && echo normal && sleep 3 && echo normal && echo normal "}))).build();
		Application app =  Application.builder().uuid(UUID.randomUUID())
				                                .definition(daf).build();
		DockerExecutor de = new DockerExecutor();
		de.setLogEntryRepository(logEntryRepository);
		de.setRunSessionRepository(runSessionRepository);
		
		RunSession session = new RunSession();
		session.setApplication(app);
		de.start(app, session);
		
//		verify(logEntryRepository, times(3)).save((LogEntry)any());
		Thread.sleep(4000);
		
		verify(logEntryRepository, times(5)).save((LogEntry)any());
	}
}
