package com.example.ntier.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

/*
	Model Pojo Bean

	@NoArgsConstructor not needed.
	Finals can be retained because Lombok appears to instantiate empty object.
	@JsonProperty does not appear needed when using @AllArgsConstructor
 */
@AllArgsConstructor @Getter @ToString
public class User {

	// only using this to avoid a setter for UUID
	public static User newUser(UUID userUid, User user) {
		return new User(userUid, user.getFirstName(), user.getLastName(), user.gender,
			user.getAge(), user.getEmail()); }

	@JsonProperty("id") private final UUID userUid;
	// @JsonIgnore
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final Integer age;
	private final String email;

	public String getFullName() { return firstName + " " + lastName; }

	public int getDateOfBirth() { return LocalDate.now().minusYears(age).getYear(); }

	public enum Gender {MALE, FEMALE}
}
