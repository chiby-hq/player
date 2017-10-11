package com.github.chiby.player.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

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
	
	String contents;
	String generatedContents;
	
	ApplicationTypeEnum type;
	
	Date createdOn;
	Date lastUpdatedOn;
	
	boolean template = false;
	
	
	Map<String,String> environment = new HashMap<>();
	
	String baseImage;
	
    List<String> parameters;
    
    /**
     * Return a long string listing all environment variables
     * @return
     */
	public List<String> flattenEnvironment() {
		if(environment==null){
			return new ArrayList<String>();
		}else return environment.entrySet()
	            .stream()
	            .map(entry -> entry.getKey() + "=" + entry.getValue()+"")
	            .collect(Collectors.toList());
	}
    
    

	
}
