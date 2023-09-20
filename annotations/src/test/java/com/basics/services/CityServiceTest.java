package com.basics.services;

import com.basics.model.City;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.util.UtilityMain.EOL;
import static com.basics.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CityServiceTest {

	@Test void testing( ) {

		StringBuilder sb = new StringBuilder();
		CityService cityService = mock(CityService.class);

		sb.append("testing: ").append(cityService.toString());
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test void findAll( ) {

		StringBuilder sb = new StringBuilder();
		String FRMT = "\n\t%02d %-15s %,9d";
		Random random = new Random();
		String cityNames = "Stowe Akron Homerville CanalFulton NorthCanton LakeCable Canton Massillon Columbus";

		List<City> cityList = new ArrayList<>();
		AtomicInteger ai = new AtomicInteger();
		Arrays.stream(cityNames.split(" ")).sorted().forEach( cityName -> {

			City city = new City();
			city.setId((long) ai.incrementAndGet());
			city.setName(cityName);
			city.setPopulation(random.nextInt(1000000));
			cityList.add(city);
		});
		CityService cityService = mock(CityService.class);
		when(cityService.findAll()).thenReturn(cityList);

		List<City> citiesFound = cityService.findAll();
		for ( City city : citiesFound ) {
			sb.append(String.format(FRMT, city.getId(), city.getName(), city.getPopulation()));
		}
		sb.append(EOL).append("total: ").append(citiesFound.size());
		System.out.println(sb);
		assertNotNull(sb);
	}
}