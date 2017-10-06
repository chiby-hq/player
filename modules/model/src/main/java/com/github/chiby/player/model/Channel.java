package com.github.chiby.player.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.github.chiby.player.model.Application.ApplicationBuilder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Channel {
	@Id UUID uuid = UUID.randomUUID();

	User author;
	List<Playlist> playlists;
}
