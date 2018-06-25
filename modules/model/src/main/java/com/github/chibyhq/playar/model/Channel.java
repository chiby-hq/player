package com.github.chibyhq.playar.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Entity
public class Channel {
	@Id UUID uuid = UUID.randomUUID();

	User author;
	List<Playlist> playlists;
}
