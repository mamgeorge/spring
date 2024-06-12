package com.example.ntier.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

/*
	Model Pojo Bean
	@NoArgsConstructor not needed.
	Finals can be retained because Lombok appears to instantiate empty object.
 */
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
