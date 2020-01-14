package com.github.chibyhq.store.model.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.chibyhq.playar.model.Playlist;
import com.github.chibyhq.playar.model.QPlaylist;
import com.github.chibyhq.playar.model.User;
import com.querydsl.core.types.Predicate;

public class PlaylistRepositoryImpl implements PlaylistRepositoryCustom {

	@Autowired
	PlaylistRepository repo;
	
	@Override
	public Optional<Playlist> findOneByNameAndUser(String name, User user) {
		QPlaylist playlist = new QPlaylist("playlist");
		Predicate p = playlist.name.eq(name).and(playlist.author.uuid.eq(user.uuid));
		Optional<Playlist> result = repo.findOne(p);
		return result;
	}




	

}
