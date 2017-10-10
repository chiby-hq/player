package com.github.chiby.player.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@QueryEntity
public class Playlist {
	public Playlist(String id, String name, User author){
		this.id = id;
		this.name = name;
		this.author = author;
	}
	public Playlist(){
		
	}
	
	@Id public String id = UUID.randomUUID().toString();

	public String name;
	public User author;
	public Date creationDate;
   
	public List<Application> gadgets;
   
}
