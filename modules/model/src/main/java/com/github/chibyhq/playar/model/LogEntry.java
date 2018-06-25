package com.github.chibyhq.playar.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class LogEntry {
//	RunSession runSession;
	@Id public String uuid = UUID.randomUUID().toString();
	Date startedAt;
	Boolean error;
	String line;
}
