package com.github.chiby.player.model.repositories;

import java.util.List;

import com.github.chiby.player.model.Playlist;
import com.github.chiby.player.model.User;

public interface PlaylistRepositoryCustom {
	public List<Playlist> findByUser(User user);
}
