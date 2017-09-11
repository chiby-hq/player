package com.github.chiby.store;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import com.github.chiby.player.model.Playlist;
import com.github.chiby.player.model.User;
import com.github.chiby.store.model.repositories.PlaylistRepository;
import com.github.chiby.store.model.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableMapRepositories
public class StoreApplicationTest {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PlaylistRepository playlistRepo;
	
	@Test
	public void contextLoads() {
		assertEquals(1, playlistRepo.count());
		
		User john = userRepo.findOneByNickname("John");
		
		assertNotNull(playlistRepo.findOneByNameAndUser("default", john));
		
		assertNull(playlistRepo.findOneByNameAndUser("non-existent", john));
	}

}
