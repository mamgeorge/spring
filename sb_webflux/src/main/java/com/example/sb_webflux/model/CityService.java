package com.example.sb_webflux.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

// @Service annotation declares CityService to be a service class; a class that provides business services
// @Autowired annotation marks cityRepository field to be injected with CityRepository

@Service
public class CityService implements ICityService {

	@Autowired private CityRepository cityRepository;

	@Autowired @Qualifier("jdbcScheduler") private Scheduler jdbcScheduler;
	@Autowired private TransactionTemplate transactionTemplate;

	@Override public Mono<City> findById(Long id) {
		//
		// return cityRepository.findById(id);
		Mono<City> monoCity = Mono
			.fromCallable(() -> this.cityRepository.findById(id).get())
			.subscribeOn(jdbcScheduler);
		return monoCity;
	}

	@Override
	public Mono<City> save(City city) {
		//
		// return cityRepository.save(city);
		Mono<City> monoCity = Mono.fromCallable(() -> transactionTemplate.execute(status -> {
			City savedCity = cityRepository.save(city);
			return savedCity;
		})).subscribeOn(jdbcScheduler);
		return monoCity;
	}

	@Override
	public Flux<City> findAll() {
		//
		// return cityRepository.findAll();
		Flux<City> defer = Flux.defer(() -> Flux.fromIterable(this.cityRepository.findAll()));
		return defer.subscribeOn(jdbcScheduler);
	}

	/*
	public List<City> findAll( ) { 
		//
        var listCities = new ArrayList<City>();
        listCities.add( new City( 1L, "Bratislava", 432000 ) );
		listCities.add( new City( 2L, "Budapest", 1759000 ) );
        return listCities;	
	}
	*/
}