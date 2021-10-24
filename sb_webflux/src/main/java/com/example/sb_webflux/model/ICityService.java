package com.example.sb_webflux.model;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICityService {

	Mono<City> findById(Long id);

	Mono<City> save(City city);

	Flux<City> findAll();
}
