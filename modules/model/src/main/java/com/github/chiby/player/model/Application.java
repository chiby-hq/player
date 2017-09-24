package com.github.chiby.player.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@QueryEntity
public class Application {

	@Id UUID uuid;
	String title;
    User author;
	String description;
	String avatar;
	
	Date createdOn;
	Date lastUpdatedOn;
	
	
	IApplicationDefinition definition;
}
