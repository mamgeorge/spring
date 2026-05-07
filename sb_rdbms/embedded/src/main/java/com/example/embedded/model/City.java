package com.example.embedded.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor

@Schema(description = "Represents a City of the World")
@Getter @Setter @Entity @Table( name = "cities" )
public class City {

	@Schema(description = "Unique identifier of city", example = "1", accessMode = AUTO)
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id" ) private Long id;

	@Schema(description = "Unique name of city", example = "Columbus", requiredMode  = REQUIRED)
	@Column( name = "name" ) private String name;
	@Column( name = "population" ) private int population;
	@Column( name = "created" ) private Date created;
	@Column( name = "updated" ) private Timestamp updated;
}
