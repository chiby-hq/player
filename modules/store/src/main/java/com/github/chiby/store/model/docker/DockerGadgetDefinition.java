package com.github.chiby.store.model.docker;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.github.chiby.player.model.IGadgetDefinition;
import com.github.chiby.player.model.secret.ISecret;

import lombok.Data;

/**
 * Defines a gadget via a Docker container application.
 * @author bcopy
 *
 */
@Data
public class DockerGadgetDefinition implements IGadgetDefinition{
	@Id UUID uuid;
	String image;
    ISecret credentials;
    Map<String,String> environment;
    String tarGzLocation;
    String gitLocation;
}
