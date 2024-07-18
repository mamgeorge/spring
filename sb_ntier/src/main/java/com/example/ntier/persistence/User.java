package com.example.ntier.persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

/*
	Model Pojo Bean

	Finals can be retained because Lombok appears to instantiate empty object.
	@NoArgsConstructor not needed.
	@JsonProperty does not appear needed when using @AllArgsConstructor
	@NotNull requires @Validated & @Valid in Controller
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
	@NotBlank (message = "firstName required") private final String firstName;
	@NotBlank (message = "lastName required") private final String lastName;
	@NotBlank (message = "gender required") private final Gender gender;
	@NotBlank (message = "age required")  @Max(value=120) @Min(value=12) private final Integer age;
	@NotBlank (message = "email required")  @Email private final String email;

	public String getFullName( ) { return firstName + " " + lastName; }

	public int getDateOfBirth( ) {
		int dateOfBirth = 0;
		dateOfBirth = LocalDate.now().minusYears(age).getYear();
		System.out.println("dateOfBirth: " + dateOfBirth);
		return dateOfBirth;
	}

	public enum Gender {MALE, FEMALE}

	@Override public String toString( ) {

		String json = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(this); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return json;
	}
}
