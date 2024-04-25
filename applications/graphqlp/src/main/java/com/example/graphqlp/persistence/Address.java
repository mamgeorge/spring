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
@Entity @Table( schema = "public", name = "address" )
public class Address {

	// address_id, address, address2, district, city_id, postal_code, phone, last_update
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "address_id" ) private Integer address_id;
	private String address;
	private String address2;
	private String district;
	private Integer city_id;
	private String postal_code;
	private String phone;
	private Timestamp last_update;
}
