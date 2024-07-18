package com.example.ntier.configs;

import com.example.ntier.persistence.User;
import com.example.ntier.persistence.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.ntier.persistence.User.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

// https://docs.spring.io/spring-framework/reference/testing/webtestclient.html
// @ExtendWith(SpringExtension.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT )
public class NtierControllerTest {

	@Autowired private UserService userService;

	private NtierController ntierController;

	@BeforeEach void init( ) { ntierController = new NtierController(userService); }

	@Test void getAllUsers( ) {

		List<User> users = ntierController.getAllUsers(MALE.name());
		users.forEach(user -> System.out.print(user.getFirstName() + " "));
		assertThat(users).hasSizeGreaterThan(1);
		assertThat(users.get(0).getUserUid()).isInstanceOf(UUID.class);
		assertThat(users.get(0).getUserUid()).isNotNull();
	}

	@Test void getUser( ) {

		List<User> users = ntierController.getAllUsers(MALE.name());
		String userUid = users.get(0).getUserUid().toString();
		Optional<String> optional = Optional.of(userUid);
		ResponseEntity<?> response = ntierController.getUser(optional);
		User user = (User) response.getBody();
		System.out.println(user.toString());
		assertThat(response).isNotNull();
	}

	@Test void getUserRnd( ) {

		ResponseEntity<User> response = ntierController.getUserRnd();
		User user = response.getBody();
		System.out.println(user.toString());
		assertThat(response).isNotNull();
	}

	@Test void updateUser( ) {

		ResponseEntity<User> responseRnd = ntierController.getUserRnd();
		User user = responseRnd.getBody();
		String userUid = user.getUserUid().toString();
		ReflectionTestUtils.setField(user, "age", 105);
		ResponseEntity<Integer> responseUpd = ntierController.updateUser(user);
		System.out.println(responseUpd.getBody());

		Optional<String> optional = Optional.of(userUid);
		ResponseEntity<?> responseGet = ntierController.getUser(optional);
		User userGet = (User) responseGet.getBody();
		System.out.println(userGet);
		assertThat(userGet).isNotNull();
	}

}
