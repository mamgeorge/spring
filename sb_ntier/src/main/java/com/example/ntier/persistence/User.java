package com.example.ntier.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

// Model Pojo Bean
@NoArgsConstructor @AllArgsConstructor @Getter @ToString
public class User {

	@Setter private UUID userUid;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Integer age;
	private String email;

	public enum Gender { MALE, FEMALE }
}
