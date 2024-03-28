package com.example.pgs.demo.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity // @Table( name = "actor")
public class Actor {

	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( columnDefinition = "actor_id" )
	private Long actor_id;

	private String first_name;
	private String last_name;
	private Timestamp last_update;
}
