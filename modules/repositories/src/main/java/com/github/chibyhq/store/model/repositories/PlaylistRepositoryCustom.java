package com.github.chibyhq.store.model.repositories;

import com.github.chibyhq.playar.model.Playlist;
import com.github.chibyhq.playar.model.User;

public interface PlaylistRepositoryCustom {
	public Playlist findOneByNameAndUser(String name, User user);
}
