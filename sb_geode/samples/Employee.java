package com.example.embedded.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Region("employees")
//@Entity @Table(name = "employees")
// @Entity @Table(name = "employee")
public class Employee {

	@Id
	private Long id;
	public String firstname;
	public String lastname;
	public String email;
	public double salary;

	@PersistenceConstructor
	public Employee(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
}
