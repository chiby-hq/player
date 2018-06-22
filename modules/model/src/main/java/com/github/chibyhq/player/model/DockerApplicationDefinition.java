package com.github.chibyhq.player.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

import com.github.chibyhq.player.model.secret.ISecret;

import lombok.Builder;
import lombok.Data;

/**
 * Defines a gadget via a Docker container application.
 * @author bcopy
 *
 */
@Data
@Builder(toBuilder = true)
public class DockerApplicationDefinition implements IApplicationDefinition{
	@Id UUID uuid;
	String image;
    ISecret credentials;
    Map<String,String> environment;
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
