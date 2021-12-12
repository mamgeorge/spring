package com.example.sb_webflux.configuration;

import com.example.sb_webflux.model.City;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientCity {

	private final WebClient webClientCity;

	public WebClientCity() {
		webClientCity = WebClient.create(RouterAny.URL_ROUTE);
	}

	public Mono<City> webClient4City(String id) {
		//
		Mono<City> monoCity = webClientCity.get()
			.uri(RouterAny.URI_CITY, id)
			.retrieve()
			.bodyToMono(City.class);
		monoCity.subscribe(System.out::println);
		return monoCity;
	}

	public Flux<City> webClient4Cities() {
		//
		Flux<City> fluxCities = webClientCity.get()
			.uri(RouterAny.URI_CITIES)
			.retrieve()
			.bodyToFlux(City.class);
		fluxCities.subscribe(System.out::println);
		return fluxCities;
	}
}
