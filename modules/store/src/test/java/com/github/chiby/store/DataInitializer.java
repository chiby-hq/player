package com.github.chiby.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.chiby.player.model.Playlist;
import com.github.chiby.player.model.User;
import com.github.chiby.store.model.repositories.PlaylistRepository;
import com.github.chiby.store.model.repositories.UserRepository;

@Component
public class DataInitializer implements ApplicationRunner {

	@Autowired
	PlaylistRepository playlistRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		User john = new User("1234", "John", null );
		userRepository.save(john);
		
		Playlist playlist = new Playlist("12", "default", john);
		
		playlistRepository.save(playlist);

	}

}
