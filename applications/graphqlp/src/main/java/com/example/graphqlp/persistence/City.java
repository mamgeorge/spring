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
@Entity @Table( schema = "public", name = "city" )
public class City {

	// city_id, city, country_id, last_update
	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "city_id" ) private Integer city_id;
	@Column( name = "city" ) private String city_name;
	@Column( name = "country_id" ) private Integer country_id;
	@Column( name = "last_update" ) private Timestamp last_update;
}
