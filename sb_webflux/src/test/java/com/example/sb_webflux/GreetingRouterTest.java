package com.example.sb_webflux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingRouterTest {

	// SpringBoot has a `WebTestClient` configured to request "localhost:RANDOM_PORT"
	@Autowired private WebTestClient webTestClient;
	private static final String ASSERTION = "ASSERTION";

	@Test public void test_showHello() {
		//
		String EXPECTED = "Hello, Spring!";
		webTestClient
			.get().uri(RouterGreeting.URI_LINK)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk() // use dedicated DSL to test assertions against response
			.expectBody(Greeting.class).value(greeting -> {
				Assert.isTrue(greeting.getMessage().contains(EXPECTED), ASSERTION);
				System.out.println("greeting: " + greeting);
			}
		);
	}
}