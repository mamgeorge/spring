package com.example.sb_webflux.configuration;

import com.example.sb_webflux.model.Greeting;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class WebClientGreeting {

	private final WebClient webClientGreet;

	// Spring Boot auto-configures a `WebClient.Builder` with defaults to create a dedicated `WebClient` for
	// our component.
	public WebClientGreeting(WebClient.Builder builder) {
		this.webClientGreet = builder.baseUrl(RouterAny.URL_ROUTE).build();
	}

	public Mono<String> getMessage() {
		Mono<String> monoString = this.webClientGreet.get()
			.uri(RouterAny.URI_GREET)
			.accept(APPLICATION_JSON)
			.retrieve()
			.bodyToMono(Greeting.class)
			.map(Greeting::getMessage);
		monoString.subscribe(System.out::println);
		return monoString;
	}
}