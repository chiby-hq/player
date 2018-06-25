package com.github.chibyhq.playar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import com.querydsl.core.annotations.QueryEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@QueryEntity
@Entity
public class User {
   @Id public String uuid = UUID.randomUUID().toString();
   public String nickname;
   /**
    * URL pointing to the user's avatar
    */
   public String avatar;
}
