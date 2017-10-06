package com.github.chiby.player.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@QueryEntity
public class Application {

	@Id UUID uuid = UUID.randomUUID();
	String title;
    User author;
	String description;
	String avatar;
	
	@JsonIgnore	String contents;
	@JsonIgnore String generatedContents;
	
	ApplicationTypeEnum type;
	
	Date createdOn;
	Date lastUpdatedOn;
	
	
	IApplicationDefinition definition;
}
