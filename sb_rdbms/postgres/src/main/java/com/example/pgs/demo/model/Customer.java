package com.example.pgs.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity // @Table( name = "customer")
public class Customer {

	// customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active
	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( columnDefinition = "customer_id" )
	private int customer_id;

	private int store_id;
	private String first_name;
	private String last_name;
	private String email;
	private int address_id;
	private boolean activebool;
	private Date create_date;
	private Timestamp last_update;
	private int active;
}
