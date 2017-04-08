package com.github.chiby.player.model;


import lombok.Data;

import java.util.UUID;

import org.springframework.data.annotation.Id;

@Data
public class User {
   @Id String uuid = UUID.randomUUID().toString();
   String nickname;
   /**
    * URL pointing to the user's avatar
    */
   String avatar;
}
