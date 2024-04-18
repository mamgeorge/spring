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
@Entity @Table( name = "city", schema = "public")
public class City {

	// city_id, city, country_id, last_update
	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "city_id" ) private Integer city_id;
	@Column( name = "city" ) private String city;
	@Column( name = "country_id" ) private Integer country_id;
	@Column( name = "last_update" ) private Timestamp last_update;
}
