package com.example.graphqlp.persistence;

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
@Entity @Table( schema = "public", name = "customer" )
public class Customer {

	// city_id, city, country_id, last_update
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer customer_id;
	private Integer store_id;
	private Integer active;
	private Boolean activebool;
	private Integer address_id;
	private String email;
	private String first_name;
	private String last_name;
	private Timestamp create_date;
	private Timestamp last_update;
}