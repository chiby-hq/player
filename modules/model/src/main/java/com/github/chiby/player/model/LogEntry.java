package com.github.chiby.player.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogEntry {
//	RunSession runSession;
	@Id public String uuid = UUID.randomUUID().toString();
	Date startedAt;
	Boolean error;
	String line;
}
