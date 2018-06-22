package com.github.chibyhq.store.model.repositories;

import com.github.chibyhq.player.model.Playlist;
import com.github.chibyhq.player.model.User;

public interface PlaylistRepositoryCustom {
	public Playlist findOneByNameAndUser(String name, User user);
}
