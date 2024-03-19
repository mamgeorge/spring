package com.basics.testmore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "cities" )
public class City {

	// id name population
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	private String name;
	private int population;

	public City(String name, int population) {
		this.name = name;
		this.population = population;
	}

	@Override
	public int hashCode( ) {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.id);
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + this.population;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		//
		if ( this == object ) { return true; }
		if ( object == null ) { return false; }
		if ( getClass() != object.getClass() ) { return false; }
		final City otherCity = (City) object;
		if ( this.population != otherCity.population ) { return false; }
		if ( !Objects.equals(this.name, otherCity.name) ) { return false; }
		return Objects.equals(this.id, otherCity.id);
	}

	@Override
	public String toString( ) {
		//
		String builder = "City" + "{" +
			"id=" + id + ", " +
			"name=" + name + ", " +
			"population=" + population +
			"}";

		return builder;
	}
}
