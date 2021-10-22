package com.example.sb_webflux;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class RouterGreeting {

	public static final String URI_LINK = "/showHello";

	@Bean
	public RouterFunction<ServerResponse> route(HandlerGreeting handlerGreeting) {
		//
		RouterFunction<ServerResponse> routerFunction = RouterFunctions
			.route(GET(URI_LINK)
				.and(accept(APPLICATION_JSON)), handlerGreeting::showHello);
		return routerFunction;
	}
}