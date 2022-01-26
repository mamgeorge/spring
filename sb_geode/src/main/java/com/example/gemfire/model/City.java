package com.example.gemfire.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "cities")
public class City {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;
	private String	name;
	private int		population;

	public City(String name, int population) { this.name = name; this.population = population; }
}
