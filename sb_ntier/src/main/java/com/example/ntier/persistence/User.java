package com.example.ntier.persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

/*
	Model Pojo Bean

	@NoArgsConstructor not needed.
	Finals can be retained because Lombok appears to instantiate empty object.
	@JsonProperty does not appear needed when using @AllArgsConstructor
 */
@JsonIgnoreProperties( ignoreUnknown = true )
@AllArgsConstructor @Getter
public class User {

	// only using this to avoid a setter for UUID
	public static User newUser(UUID userUid, User user) {
		return new User(userUid, user.getFirstName(), user.getLastName(), user.gender,
			user.getAge(), user.getEmail());
	}

	@JsonProperty( "id" ) private final UUID userUid;
	// @JsonIgnore
	private final String firstName;
	private final String lastName;
	private final Gender gender;
	private final Integer age;
	private final String email;

	public String getFullName( ) { return firstName + " " + lastName; }

	public int getDateOfBirth( ) { return LocalDate.now().minusYears(age).getYear(); }

	public enum Gender {MALE, FEMALE}

	@Override public String toString(){

		String json = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(this); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return json;
	}
}
