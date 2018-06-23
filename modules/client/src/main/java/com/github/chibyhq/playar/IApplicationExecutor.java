package com.github.chibyhq.playar;

import java.nio.file.Path;

import com.github.chibyhq.playar.model.Application;
import com.github.chibyhq.playar.model.RunSession;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

public interface IApplicationExecutor {

	String start(Application application, RunSession session, Path applicationHome) throws Exception;

	void stop(RunSession session, Boolean cleanupRuntime)
			throws DockerCertificateException, DockerException, InterruptedException;

}