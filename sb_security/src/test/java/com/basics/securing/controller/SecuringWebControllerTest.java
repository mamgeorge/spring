package com.basics.securing.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecuringWebControllerTest {

	SecuringWebController securingWebController = new SecuringWebController();

	@Test
	void roots( ) {

		String response = securingWebController.roots();
		System.out.println("response: " + response);
		assertNotNull(response);
	}

	@Test
	void home( ) {

		String response = securingWebController.home();
		System.out.println("response: " + response);
		assertNotNull(response);
	}

	@Test
	void hello( ) {

		String response = securingWebController.hello();
		System.out.println("response: " + response);
		assertNotNull(response);
	}

	@Test
	void login( ) {

		String response = securingWebController.login();
		System.out.println("response: " + response);
		assertNotNull(response);
	}

	@Test
	void times( ) {

		String response = securingWebController.times();
		System.out.println("response: " + response);
		assertNotNull(response);
	}
}