package com.basics.testmore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
@Entity @Table( name = "countries" )
public class Country {

	// id, continent, abbr, country, code2, code3, number
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	public Long id;
	public String continent;
	public String abbr;
	public String country;
	public String code2;
	public String code3;
	public Long number;

	public Country(Long id, String continent, String abbr, String country, String code2, String code3,
		Long number) {
		//
		this.id = id;
		this.continent = continent;
		this.abbr = abbr;
		this.country = country;
		this.code2 = code2;
		this.code3 = code3;
		this.number = number;
	}

	@Override
	public String toString( ) {
		//
		String builder = "Country" + "{" +
			"id=" + id + ", " +
			"continent=" + continent + ", " +
			"abbr=" + abbr + ", " +
			"country=" + country + ", " +
			"code2=" + code2 + ", " +
			"code3=" + code3 + ", " +
			"number=" + number +
			"}";

		return builder;
	}
}
