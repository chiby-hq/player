package com.github.chibyhq.store.model.repositories;

import java.util.Optional;

import com.github.chibyhq.playar.model.Playlist;
import com.github.chibyhq.playar.model.User;

public interface PlaylistRepositoryCustom {
	public Optional<Playlist> findOneByNameAndUser(String name, User user);
}
