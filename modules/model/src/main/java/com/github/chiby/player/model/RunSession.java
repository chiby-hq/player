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
@Builder(toBuilder = true)
public class RunSession {
   Application application;
   @Id public UUID uuid = UUID.randomUUID();
   Date startedAt = new Date();
   Date stoppedAt;
   Boolean running= false;
   Boolean stopped = true;
   Boolean initialized=false;
   List<LogEntry> logEntries;
   String executionId;
   String exitMessage;
   Integer exitCode;
}
