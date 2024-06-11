package com.example.ntier.configs;

import com.example.ntier.persistence.User;
import com.example.ntier.persistence.UserDaoData;
import com.example.ntier.persistence.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith( { MockitoExtension.class } )
class NtierControllerTest {

	private Message message;
	private NtierController ntierController;
	@Mock private UserService userService;

	@BeforeEach void setUp( ) {
		message = new Message(Instant.now().toString());
		ntierController = new NtierController(userService);
	}

	@Test void root( ) {

		String txtLine = ntierController.root();
		System.out.println(txtLine);
		assertThat(txtLine).isNotNull();
	}

	@Test void getMessage( ) {

		message = ntierController.getMessage();
		System.out.println(message);
		assertThat(message).isNotNull();
	}

	@Test void getAllUsers( ) {

		when(ntierController.getAllUsers("")).thenReturn(new ArrayList<User>());
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
}