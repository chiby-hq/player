package com.github.chiby.store.shipping;

import com.github.chiby.player.model.Application;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.ExecCreateParam;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ExecCreation;

/**
 * Converts an Application into a Docker container and allows to
 * run it via a remote VNC session or locally.
 * @author bcopy
 *
 */
public class DockerExecutor {
	public void debug(Application application) throws DockerCertificateException, DockerException, InterruptedException {
		DockerClient docker = DefaultDockerClient.fromEnv().build();
		ExecCreation ec =	docker.execCreate("", new String[]{}, new ExecCreateParam[]{});
		// Attach the output stream to an outgoing stream, for instance WS connection
		
		
		
	}
	
}
