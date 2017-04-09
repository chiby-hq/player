package com.github.chiby.player.model.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.chiby.player.model.Playlist;
import com.github.chiby.player.model.QPlaylist;
import com.github.chiby.player.model.User;
import com.querydsl.core.types.Predicate;

public class PlaylistRepositoryImpl implements PlaylistRepositoryCustom {

	@Autowired
	PlaylistRepository repo;
	
	@Override
	public Playlist findOneByNameAndUser(String name, User user) {
		QPlaylist playlist = new QPlaylist("playlist");
		Predicate p = playlist.name.eq(name).and(playlist.author.uuid.eq(user.uuid));
		Playlist result = repo.findOne(p);
		return result;
	}




	

}
