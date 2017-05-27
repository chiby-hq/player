package com.github.chiby.player.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {

	@Id UUID uuid;
	String title;
    User author;
	String description;
	
	IApplicationDefinition definition;
}
