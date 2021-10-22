package com.example.sb_webflux;

import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class WebClientGreeting {

	private final WebClient webClient;
	private final String URL_ROUTE = "http://localhost:8080";

	// Spring Boot auto-configures a `WebClient.Builder` with defaults to create a dedicated `WebClient` for
	// our component.
	public WebClientGreeting(WebClient.Builder wcBuilder) {
		this.webClient = wcBuilder.baseUrl(URL_ROUTE).build();
	}

	public Mono<String> getMessage() {
		return this.webClient.get().uri(RouterGreeting.URI_LINK).accept(APPLICATION_JSON)
			.retrieve()
			.bodyToMono(Greeting.class)
			.map(Greeting::getMessage);
	}
}