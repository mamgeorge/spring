package com.basics.securing.model;

import java.util.Objects;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "countries")
public class Country implements Comparable<Country> {

	//  continent, abbr, country, code2, code3, number

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	public String continent;
	public String abbr;
	public String country;
	public String code2;
	public String code3;
	public Long number;

	public Country( ) {	}

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

	public String getContinent( ) { return continent; }
	public void setContinent( String continent ) { this.continent = continent; }

	@Override
	public int hashCode( ) {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode( this.id );
		hash = 79 * hash + Objects.hashCode( this.continent);
		hash = 79 * hash + Objects.hashCode( this.abbr);
		hash = 79 * hash + Objects.hashCode( this.country);
		hash = 79 * hash + Objects.hashCode( this.code2);
		hash = 79 * hash + Objects.hashCode( this.code3);
		hash = 79 * hash + Objects.hashCode( this.number);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		//
		if ( this == object ) { return true; }
		if ( object == null ) { return false; }
		if ( getClass( ) != object.getClass( ) ) { return false; }
		final Country otherCountry = ( Country ) object;
		if ( this.number != otherCountry.number ) { return false; }
		if ( !Objects.equals( this.code3, otherCountry.code3 ) ) { return false; }
		return Objects.equals( this.id, otherCountry.id );
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
	
	@Override
	public int compareTo(Country country) {
		/** 
			Sorting by id. compareTo should return 
			< 0 if this(keyword) is supposed to be less than country,
			> 0 if this is supposed to be greater than object country, and 
			= 0 if they are supposed to be equal.
		*/
		int intLast = this.id.compareTo(country.id);
		// int intLast = this.continent.compareTo(country.continent);
		//
		// sort by country if last was sorted by continent and they were the same  
		return intLast == 0 ? this.country.compareTo(country.country) : intLast;
	}
}
