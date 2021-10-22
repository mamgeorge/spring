package com.example.sb_webflux;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class HandlerGreeting {

	private static final String FRMT = "<pre>\t%02d %-15s %9d</pre>\n";

	public Mono<ServerResponse> showHello(ServerRequest serverRequest) {
		//
		// List<City> cities = cityService.findAll();
		String txtLine = "Hello, Spring! " + Instant.now();
		Greeting greeting = new Greeting(txtLine);
		return ServerResponse
			.ok()
			.contentType(APPLICATION_JSON)
			.body(BodyInserters.fromValue(greeting));
	}
}