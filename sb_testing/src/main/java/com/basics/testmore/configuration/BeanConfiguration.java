package com.basics.testmore.configuration;

import com.basics.testmore.model.City;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // @Import(RepositoryRestConfiguration.class)
public class BeanConfiguration {

	@Bean @Qualifier("CityBean") public City cityBean() {
		//
		City city = new City("Georgetown", 123000);
		city.setId(5000L);
		return city;
	}

	@Bean public City cityLegume() {
		//
		City city = new City("Annapolis", 750000);
		city.setId(6000L);
		return city;
	}
}
