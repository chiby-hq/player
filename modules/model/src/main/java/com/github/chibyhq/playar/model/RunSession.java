package com.github.chibyhq.playar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class RunSession {
   UUID applicationUUID;
   @Id 
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(columnDefinition = "BINARY(16)")
   public UUID uuid;
   Date startedAt = new Date();
   Date stoppedAt;
   Boolean running= false;
   Boolean stopped = true;
   Boolean initialized=false;
   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
   List<LogEntry> logEntries = new ArrayList<>();
   String executionId;
   String exitMessage;
   Integer exitCode;
}
