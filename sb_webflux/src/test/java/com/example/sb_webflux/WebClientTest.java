package com.example.sb_webflux;

import com.example.sb_webflux.configuration.RouterAny;
import com.example.sb_webflux.model.City;
import com.example.sb_webflux.model.Greeting;
import java.util.ArrayList;
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
public class WebClientTest {

	// SpringBoot has a `WebTestClient` configured to request "localhost:RANDOM_PORT"
	@Autowired private WebTestClient webTestClient;
	private static final String ASSERTION = "ASSERTION";

	@Test public void testWC_getMessage() {
		//
		String EXPECTED = "Hello, Spring!";
		webTestClient
			.get().uri(RouterAny.URI_GREET)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk() // use dedicated DSL to test assertions against response
			.expectBody(Greeting.class).value(greeting -> {
				Assert.isTrue(greeting.getMessage().contains(EXPECTED), ASSERTION);
				System.out.println("greeting: " + greeting);
			}
		);
	}

	@Test public void testWC_getCity() {
		//
		String EXPECTED = "MexicoCity";
		String CITY_ID = "5";
		webTestClient
			.get().uri(RouterAny.URI_CITY, CITY_ID)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk() // use dedicated DSL to test assertions against response
			.expectBody(City.class).value(city -> {
				Assert.isTrue(city.getName().contains(EXPECTED), ASSERTION);
				System.out.println("city: " + city);
			}
		);
	}

	@Test public void testWC_getCities() {
		//
		String EXPECTED = "MexicoCity";
		webTestClient
			.get().uri(RouterAny.URI_CITIES)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(ArrayList.class).value(cities -> {
				Assert.isTrue(cities.toString().contains(EXPECTED), ASSERTION);
				System.out.println("cities[" + cities.size() + "]: " + cities);
			}
		);
	}
}