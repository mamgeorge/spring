package com.example.graphqlp.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "actor", schema = "public")
public class Actor {

	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "city_id" )
	private int city_id;

	@Column( name = "actor_id" ) private Integer actor_id;
	@Column( name = "first_name" ) private String first_name;
	@Column( name = "last_name" ) private String last_name;
	@Column( name = "last_update" ) private Timestamp last_update;
}
