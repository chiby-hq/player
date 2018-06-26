package com.github.chibyhq.store.model.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.github.chibyhq.playar.model.User;

public interface UserRepository extends CrudRepository<User, UUID> {

	User findOneByNickname(String nickname);
}
