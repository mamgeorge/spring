package com.basics.testmore.model;

import org.junit.jupiter.api.Test;

import static com.basics.testmore.util.UtilityMain.exposeObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CityTest {

	@Test
	void testEquals( ) {

		boolean isCity = false;
		City city = new City();

		isCity = city.equals(new City());
		System.out.println("isCity: " + isCity);

		city.setName("Columbus");
		isCity = city.equals(new City());
		System.out.println("isCity: " + isCity);

		System.out.println(exposeObject(city));
		assertNotNull(city);
	}
}