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
	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "address_id" ) private Integer id;

	@Column( name = "address" ) private String address;
	@Column( name = "address2" ) private String address2;
	@Column( name = "district" ) private String district;
	@Column( name = "city_id" ) private Integer city_id;
	@Column( name = "postal_code" ) private String postal_code;
	@Column( name = "phone" ) private String phone;
	@Column( name = "last_update" ) private Timestamp last_update;
}
