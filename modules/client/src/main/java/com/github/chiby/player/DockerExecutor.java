package com.github.chiby.player;

import com.github.chiby.player.model.Application;
import com.github.chiby.player.model.DockerApplicationDefinition;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;

public class DockerExecutor {
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

}
