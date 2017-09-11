package com.github.chiby.store.model.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.github.chiby.player.DockerExecutor;
import com.github.chiby.player.model.RunSession;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

/**
 * 
 * @author bcopy
 *
 */

@RepositoryEventHandler(RunSession.class)
@Component
public class RunSessionHandler {
	@Autowired
	DockerExecutor executor;

	/**
	 * When a new run session is created, we immediately connect it to a
	 * Docker run. We will post docker logs outputs as log entries connected
	 * this this run session asynchronously.
	 * @param runSession The newly created run session.
	 * @throws InterruptedException 
	 * @throws DockerException 
	 * @throws DockerCertificateException 
	 */
	@HandleAfterCreate
	public void connectRunSessionToExecution(RunSession runSession) {
		// 
		try {
			executor.debug(runSession.getApplication());
		} catch (DockerCertificateException | DockerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@HandleBeforeSave
	public void interruptRunSession(RunSession runSession) {
		if(!runSession.getRunning()){
//			executor.stop()
		}
		
	}
}
