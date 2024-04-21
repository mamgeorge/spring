package com.example.graphqlp.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
@Entity @Table( schema = "public", name = "actor" )
public class Actor {

	/*
		actor_id, first_name, last_name, last_update
		note! repo does NOT read actor table sequence; GenerationType.SEQUENCE did not help
		GRANT USAGE, UPDATE, SELECT ON SEQUENCE public.actor_actor_id_seq TO "Roles"; fails wo roles
		IDENTITY lets DB automatically increment
	*/
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "actor_id" ) private Integer actor_id;
	@Column( name = "first_name" ) private String first_name;
	@Column( name = "last_name" ) private String last_name;
	@Column( name = "last_update" ) private Timestamp last_update;
}
