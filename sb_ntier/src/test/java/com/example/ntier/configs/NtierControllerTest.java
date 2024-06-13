package com.example.ntier.configs;

import com.example.ntier.persistence.User;
import com.example.ntier.persistence.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.ntier.persistence.User.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith( { MockitoExtension.class } )
class NtierControllerTest {

	@Mock private UserService userService;
	private User user = new User(UUID.randomUUID(), "Hal", "Jordan", MALE, 30, "Hal.Jordan@email.com");
	private NtierController ntierController;

	@BeforeEach void setUp( ) { ntierController = new NtierController(userService); }

	@Test void root( ) {

		String txtLine = ntierController.root();
		System.out.println(txtLine);
		assertThat(txtLine).isNotNull();
	}

	@Test void getAllUsers( ) {

		when(ntierController.getAllUsers("")).thenReturn(new ArrayList<>());
		List<User> list = ntierController.getAllUsers("");
		System.out.println(list);
		assertThat(list).isNotNull();
	}

	@Test void getUser( ) {

		ResponseEntity<?> response;
		response = ntierController.getUser(UUID.randomUUID());
		System.out.println(response.getBody());
		assertThat(response).isNotNull();
	}

	@Test void getUserRnd() { assertThat(ntierController.getUserRnd()).isNotNull(); }

	@Test void getUserPst() {

		ResponseEntity<User> response = ntierController.getUserPst(user);
		assertThat(response).isNotNull();
	}

	@Test void insertUser() { assertThat(ntierController.insertUser(null)).isNotNull(); }

	@Test void updateUser() { assertThat(ntierController.updateUser(null)).isNotNull(); }

	@Test void deleteUser() { assertThat(ntierController.deleteUser(UUID.randomUUID())).isNotNull(); }
}