package com.example.demo.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

// Model Pojo Bean
@AllArgsConstructor @Getter @ToString
public class User {

	@Setter private UUID userUid;
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final Integer age;
	private final String email;

	public enum Gender { MALE, FEMALE }
}
