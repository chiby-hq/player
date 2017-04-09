package com.github.chiby.player.model.repositories;


import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.github.chiby.player.model.Playlist;

@RepositoryRestResource()
public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, String>, QueryDslPredicateExecutor<Playlist>, PlaylistRepositoryCustom
{
}

