package com.github.chibyhq.store.model.repositories;

import java.util.UUID;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.chibyhq.player.model.Application;

@RepositoryRestResource()
public interface ApplicationRepository extends PagingAndSortingRepository<Application, UUID>, QueryDslPredicateExecutor<Application>{

	Application findOneByTitle(@Param("title") String title);
}
