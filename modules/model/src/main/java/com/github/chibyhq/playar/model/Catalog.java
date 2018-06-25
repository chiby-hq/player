package com.github.chibyhq.playar.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
public class Catalog {
	@Id UUID uuid = UUID.randomUUID();
	List<CatalogCategory> categories;
}
