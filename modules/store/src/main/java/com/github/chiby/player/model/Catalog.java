package com.github.chiby.player.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Catalog {
	@Id UUID uuid = UUID.randomUUID();
	List<CatalogCategory> categories;
}
