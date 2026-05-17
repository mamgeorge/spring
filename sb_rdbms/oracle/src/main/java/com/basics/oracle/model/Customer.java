package com.basics.oracle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder// @EqualsAndHashCode
//@Entity @Table( name = "customers" )
public class Customer {

	// NOTE: JPA forces underscore for camelCase
	// @Column( name = "customerid", updatable = false, nullable = false )
	// @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) @Column( name = "customerid" ) 
	private Integer customerid; // schema = "main"
	private String firstname;
	private String lastname;
	private String company;
	private String address;
	private String city;
	private String state;
	private String country;
	private String postalcode;
	private String phone;
	private String fax;
	private String email;
	private int supportrepid;
}
