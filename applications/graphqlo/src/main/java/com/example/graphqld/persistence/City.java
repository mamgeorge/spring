package com.example.graphqld.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "cities", schema = "app")
public class City {

	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "city_id" )
	private int city_id;

	@Column( name = "city_name" ) private String city_name;
	@Column( name = "country" ) private String country;
	@Column( name = "airport" ) private String airport;
	@Column( name = "language" ) private String language;
	@Column( name = "country_iso_code" ) private String country_iso_code;
}
