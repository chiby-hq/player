package com.github.chibyhq.playar.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@QueryEntity
@Entity
public class Playlist {
	public Playlist(UUID id, String name, User author){
		this.id = id;
		this.name = name;
		this.author = author;
	}
	public Playlist(){
		
	}
	
	@Id@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    public UUID id ;

	public String name;
	
	@ManyToOne
	public User author;
	public Date creationDate;
   
	@ElementCollection
	public List<Application> gadgets;
   
}
