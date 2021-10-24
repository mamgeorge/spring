package com.example.sb_webflux.configuration;

import com.example.sb_webflux.model.City;
import com.example.sb_webflux.model.ICityService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

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
}