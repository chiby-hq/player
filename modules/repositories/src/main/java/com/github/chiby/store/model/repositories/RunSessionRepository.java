package com.github.chiby.store.model.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.chiby.player.model.RunSession;

@RepositoryRestResource()
public interface RunSessionRepository extends CrudRepository<RunSession, UUID>{

	RunSession findOneByExecutionIdAndRunning(String executionId, Boolean running);
}
