package com.github.chiby.player.model.repositories;


import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.github.chiby.player.model.Playlist;

public interface PlaylistRepository extends CrudRepository<Playlist, String>, QueryDslPredicateExecutor<Playlist>, PlaylistRepositoryCustom
{
}

