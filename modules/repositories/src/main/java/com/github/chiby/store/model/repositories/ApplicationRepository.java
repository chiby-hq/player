package com.github.chiby.store.model.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.chiby.player.model.Application;

@RepositoryRestResource()
public interface ApplicationRepository extends PagingAndSortingRepository<Application, String>, QueryDslPredicateExecutor<Application>{

	Application findOneByName(@Param("name") String name);
}
