package com.basics.dbsqlite.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
@Getter @Setter @NoArgsConstructor // @EqualsAndHashCode
@Entity @Table( name = "customers" )
public class Customer {

	// NOTE: JPA forces underscore for camelCase
	// @Column( name = "customerid", updatable = false, nullable = false )
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "customerid" ) private Integer customerid; // schema = "main"
	@Column( name = "firstname", nullable = false ) private String firstname;
	@Column( name = "lastname", nullable = false ) private String lastname;
	@Column( name = "company" ) private String company;
	@Column( name = "address" ) private String address;
	@Column( name = "city" ) private String city;
	@Column( name = "state" ) private String state;
	@Column( name = "country" ) private String country;
	@Column( name = "postalcode" ) private String postalcode;
	@Column( name = "phone" ) private String phone;
	@Column( name = "fax" ) private String fax;
	@Column( name = "email", nullable = false ) private String email;
	@Column( name = "supportrepid" ) private int supportrepid;
}
