package com.github.chibyhq.playar.model;

import java.util.UUID;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
public class ApplicationRating {
	@Id UUID uuid;
	Application ratedApplication;
	User ratedBy;
	ScoreEnum score;
}
