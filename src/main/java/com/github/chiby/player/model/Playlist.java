package com.github.chiby.player.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Playlist {
	@Id String id;

	String name;
	User author;
    Date creationDate;
   
    List<Gadget> gadgets;
   
}
