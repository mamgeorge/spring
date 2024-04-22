package com.basics.dbsqlite.persistence;

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
@Entity @Table( name = "invoices" ) // schema = "main"
public class Invoices {

	// invoiceId invoiceDate customerId total billingAddress billingCity billingCountry billingPostalCode billingState
	// NOTE: JPA forces underscore for camelCase
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "invoiceid" )		private Integer invoiceid;
	private Timestamp invoicedate;
	private Integer customerid;
	private Float total;
	private String billingaddress;
	private String billingcity;
	private String billingcountry;
	private String billingpostalcode;
	private String billingstate;
}
