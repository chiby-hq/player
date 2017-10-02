package com.github.chiby.player.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunSession {
   Application application;
   @Id public String uuid = UUID.randomUUID().toString();
   Date startedAt = new Date();
   Boolean running= false;
   Boolean stopped = true;
   Boolean initialized=false;
   List<LogEntry> logEntries;
}