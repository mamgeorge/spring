package com.basics.testmore.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
	
/*
	public Long getId( ) { return id; }
	public void setId( Long id ) { this.id = id; }

	public String getContinent( ) { return continent; }
	public void setContinent( String continent ) { this.continent = continent; }

	public String getAbbr( ) { return abbr; }
	public void setAbbr( String abbr ) { this.abbr = abbr; }

	public String getCountry( ) { return country; }
	public void setCountry( String country ) { this.country = country; }

	public String getCode2( ) { return code2; }
	public void getCode2( String code2 ) { this.code2 = code2; }

	public String getCode3( ) { return code3; }
	public void getCode3( String code3 ) { this.code3 = code3; }

	public Long getNumber( ) { return number; }
	public void setNumber( Long number ) { this.number = number; }

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
		//
		final Country otherCountry = ( Country ) object;
		if ( this.number != otherCountry.number ) { return false; }
		if ( !Objects.equals( this.code3, otherCountry.code3 ) ) { return false; }
		return Objects.equals( this.id, otherCountry.id );
	}
*/

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
