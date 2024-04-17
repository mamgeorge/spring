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

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "address", schema = "appublicp")
public class Address {

	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "address_id" )
	private Integer address_id;

	private String address;
	private String address2;
	private String district;
	private Integer city_id;
	private String postal_code;
	private String phone;
	private String last_update;
}
