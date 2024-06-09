package com.example.ntier.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class NtierControllerTest {

	private Message message;
	private NtierController ntierController;

	@BeforeEach void setUp( ) {
		message = new Message(Instant.now().toString());
		ntierController = new NtierController();
	}

	@Test void root( ) {

		String txtLine = ntierController.root();
		System.out.println(txtLine);
		assertThat(txtLine).isNotNull();
	}

	@Test void getMessage( ) {

		Message message = ntierController.getMessage();
		System.out.println(message);
		assertThat(message).isNotNull();
	}
}