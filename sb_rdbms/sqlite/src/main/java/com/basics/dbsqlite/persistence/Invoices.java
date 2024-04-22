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
	@Column( name = "invoiceid" )		private Integer invoiceId;
	@Column( name = "invoicedate" )		private Timestamp invoiceDate;
	@Column( name = "customerid" )		private Integer customerId;
	@Column( name = "total" )			private Float total;
	@Column( name = "billingaddress" )	private String billingAddress;
	@Column( name = "billingcity" )		private String billingCity;
	@Column( name = "billingcountry" )	private String billingCountry;
	@Column( name = "billingpostalcode" )	private String billingPostalCode;
	@Column( name = "billingstate" )		private String billingState;
}
