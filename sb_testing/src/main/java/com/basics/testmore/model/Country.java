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
@Entity @Table(name = "countries")
public class Country {

	// id, continent, abbr, country, code2, code3, number
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	public String continent;
	public String abbr;
	public String country;
	public String code2;
	public String code3;
	public Long number;

	public Country( Long id, String continent, String abbr, String country, String code2, String code3, Long number ) {
		//
		this.id			= id;
		this.continent	= continent;
		this.abbr		= abbr;
		this.country	= country;
		this.code2		= code2;
		this.code3		= code3;
		this.number		= number;
	}

	@Override
	public String toString( ) {
		//
		StringBuilder builder = new StringBuilder( );
		builder
			.append( "Country"		).append( "{" )
			.append( "id="			).append( id )			.append( ", " )
			.append( "continent="	).append( continent )	.append( ", " )
			.append( "abbr="		).append( abbr )		.append( ", " )
			.append( "country="		).append( country )		.append( ", " )
			.append( "code2="		).append( code2 )		.append( ", " )
			.append( "code3="		).append( code3 )		.append( ", " )
			.append( "number="		).append( number )
			.append( "}" );

		return builder.toString( );
	}
}
