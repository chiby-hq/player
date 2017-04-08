package com.github.chiby.player.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class GadgetRating {
	@Id UUID uuid;
	Gadget ratedGadget;
	User ratedBy;
	ScoreEnum score;
}
