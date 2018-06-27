package com.github.chibyhq.store.model.repositories;


import java.util.List;
import java.util.UUID;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.chibyhq.playar.model.Playlist;

@RepositoryRestResource()
public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, UUID>, QueryDslPredicateExecutor<Playlist>, PlaylistRepositoryCustom
{
	    List<Playlist> findByName(@Param("name") String name);
}

