package com.github.chiby.store.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.chiby.player.model.RunSession;

@RepositoryRestResource()
public interface RunSessionRepository extends PagingAndSortingRepository<RunSession, String>{

}
