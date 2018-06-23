package com.github.chibyhq.playar.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class CatalogCategory {
  @Id UUID uuid = UUID.randomUUID();
  String name;
  List<Application> gadgets;
}
