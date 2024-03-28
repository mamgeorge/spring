package com.basics.dbsqlite.services;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table( name = "main.customers" )
public class Customer {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "CustomerId", updatable = false, nullable = false ) // updatable = false
	private Integer customerid; // Integer
	@Column( name = "FirstName", nullable = false )
	private String firstname;
	@Column( name = "LastName", nullable = false )
	private String lastname;
	@Column( name = "Company" )
	private String company;
	@Column( name = "Address" )
	private String address;
	@Column( name = "City" )
	private String city;
	@Column( name = "State" )
	private String state;
	@Column( name = "Country" )
	private String country;
	@Column( name = "PostalCode" )
	private String postalcode;
	@Column( name = "Phone" )
	private String phone;
	@Column( name = "Fax" )
	private String fax;
	@Column( name = "Email", nullable = false )
	private String email;
	@Column( name = "SupportRepId" )
	private int supportrepid;
}
