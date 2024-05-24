package com.example.embedded.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
@Getter @Setter
@Entity @Table( name = "cities" )
public class City {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" ) private Long id;

	@Column( name = "name" ) private String name;
	@Column( name = "population" ) private int population;
	@Column( name = "created" ) private Date created;
	@Column( name = "updated" ) private Timestamp updated;
}
