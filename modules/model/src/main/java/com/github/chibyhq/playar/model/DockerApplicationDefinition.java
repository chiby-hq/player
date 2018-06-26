package com.github.chibyhq.playar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

/**
 * Defines a gadget via a Docker container application.
 * @author bcopy
 *
 */
@Data
@Builder(toBuilder = true)
@Entity
public class DockerApplicationDefinition implements IApplicationDefinition{
	@Id UUID uuid;
	String image;
 //   ISecret credentials;
	
	@ElementCollection
	Map<String,String> environment = new HashMap<>();
	
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
