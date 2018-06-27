package com.github.chibyhq.playar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

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
@Entity
public class Application {

	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
	UUID uuid;
	String title;
    
	@ManyToOne
	User author;
	String description;
	String avatar;
	
	String contents;
	String generatedContents;
	
	ApplicationTypeEnum type;
	
	Date createdOn;
	Date lastUpdatedOn;
	
	boolean template = false;
	
	
	@ElementCollection
	Map<String,String> environment = new HashMap<>();
	
	String baseImage;
	
	@ElementCollection
	List<String> parameters = new ArrayList<>();
    
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
