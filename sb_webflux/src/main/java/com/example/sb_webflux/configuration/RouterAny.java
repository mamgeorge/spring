package com.example.sb_webflux.configuration;

import com.example.sb_webflux.model.City;
import com.example.sb_webflux.model.ICityService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration(proxyBeanMethods = false)
public class RouterAny {

	@Autowired private ICityService cityService;
	public static final String URL_ROUTE = "http://localhost:8080";
	public static final String URI_GREET = "/showHello";
	public static final String URI_HTML_INDEX = "/index";
	public static final String URI_CITY = "/city/{id}";
	public static final String URI_CITIES = "/cities";
	public static final String URI_CITYNEW = "/cityNew";

	@Bean public RouterFunction<ServerResponse> route4Greet(HandlerGreeting handlerGreeting) {
		//
		RouterFunction<ServerResponse> routerFunction = RouterFunctions
			.route(GET(URI_GREET)
				.and(accept(APPLICATION_JSON)), handlerGreeting::displayHello);
		return routerFunction;
	}

	@Bean public RouterFunction<ServerResponse> route4Html() {
		// can not use ModelAndView in webflux
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route()
			.GET(URI_HTML_INDEX, (req) -> ok()
				.render("index", Collections.emptyMap()))
			.build();
		return routerFunction;
	}

	@Bean public RouterFunction<ServerResponse> route4City() {
		//
		RouterFunction<ServerResponse> routerFunction = RouterFunctions
			.route(GET(URI_CITY), req -> ok()
				.body(cityService.findById(Long.parseLong(req.pathVariable("id"))), City.class));
		return routerFunction;
	}

	@Bean public RouterFunction<ServerResponse> route4Cities() {
		//
		RouterFunction<ServerResponse> routerFunction = RouterFunctions
			.route(GET(URI_CITIES), req -> ok()
				.body(cityService.findAll(), City.class));
		return routerFunction;
	}

	// for testing scenario
	@Bean public RouterFunction<ServerResponse> routesGeneric(HandlerUser handlerUser) {
		//
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route()
			.path("/root", builder -> builder
				.GET(URI_CITY, accept(APPLICATION_JSON), handlerUser::getUser)
				.GET(URI_CITIES, accept(APPLICATION_JSON), handlerUser::getUsers)
				.POST(URI_CITYNEW, accept(APPLICATION_JSON), handlerUser::createUser)
			)
			.build();
		return routerFunction;
	}
}


// for testing scenario
@Component
class HandlerUser {

	public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
		Mono<ServerResponse> monoServerResponse = ServerResponse
			.ok()
			.contentType(APPLICATION_JSON)
			.body(BodyInserters.fromValue("QQQ"));
		return monoServerResponse;
	}

	public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
		Mono<ServerResponse> monoServerResponse = ServerResponse
			.ok()
			.contentType(APPLICATION_JSON)
			.body(BodyInserters.fromValue("RRR"));
		return monoServerResponse;
	}

	public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
		Mono<ServerResponse> monoServerResponse = ServerResponse
			.ok()
			.contentType(APPLICATION_JSON)
			.body(BodyInserters.fromValue("SSS"));
		return monoServerResponse;
	}
}