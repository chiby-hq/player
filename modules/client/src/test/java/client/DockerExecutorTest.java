package client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;


import com.github.chiby.player.DockerExecutor;
import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.DockerApplicationDefinition;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

public class DockerExecutorTest {

	@Test
	public void test() throws DockerCertificateException, DockerException, InterruptedException {
		Map<String,String> env = new HashMap<>();
		env.put("VAR1","Hello");
		DockerApplicationDefinition daf = DockerApplicationDefinition.builder()
				        .image("busybox:latest")
				        .environment(env)
				        .parameters(Arrays.asList((new String[]{"sh","-c","echo $VAR1"}))).build();
		Application app =  Application.builder().uuid(UUID.randomUUID())
				                                .title("My Busybox counter")
				                                .definition(daf).build();
		DockerExecutor de = new DockerExecutor();
		String output = de.debug(app);
		System.out.println("Output " + output);
		Assert.assertEquals(env.get("VAR1"), output.trim());
	}

}
