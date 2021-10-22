package com.basics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity @Table(name = "cities")
public class City {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;
	private String	name;
	private int		population;

	public City() { }
	public City(String name, int population) { this.name = name; this.population = population; }

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getPopulation() { return population; }
	public void setPopulation(int population) { this.population = population; }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.id);
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + this.population;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		//
		if (this == object) { return true; }
		if (object == null) { return false; }
		if (getClass() != object.getClass()) { return false; }
		final City otherCity = (City) object;
		if (this.population != otherCity.population) { return false; }
		if (!Objects.equals(this.name, otherCity.name)) { return false; }
		return Objects.equals(this.id, otherCity.id);
	}

	@Override
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		builder
			.append("City").append("{")
			.append("id=").append(id).append(", ")
			.append("name=").append(name).append(", ")
			.append("population=").append(population)
			.append("}");

		return builder.toString();
	}
}
