package com.basics.testmore.model;

import com.basics.testmore.repository.CityRepository;
import com.basics.testmore.util.UtilityMain;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.LOGGER;
import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest @Disabled( "avoids loading instance" )
class CityRepositoryTest {

	@Resource private CityRepository cityRepository;

	// @BeforeAll public void setUp( ) throws Exception { }

	@Test void testSettersGetters( ) {
		//
		City city = new City();

		System.out.println(UtilityMain.exposeObject(city));
		assertNotNull(city);
	}

	@Test void equals( ) {
		//
		City city1 = new City();
		City city2 = new City();
		boolean isEqual = city1.equals(city2);

		System.out.println("isEqual: " + isEqual);
		assertTrue(isEqual);
	}

	@Test void findAll( ) {
		//
		String txtLines = "";
		Iterable<City> iterable = cityRepository.findAll();
		List<City> cities = new ArrayList<City>();
		for ( City city : iterable ) {
			//
			txtLines += "\n";
			txtLines += "\t" + city.getId();
			txtLines += "\t" + city.getName();
			txtLines += "\t" + city.getPopulation();
			cities.add(city);
		}
		txtLines += PAR + cities.size();
		LOGGER.info(txtLines);
		assertTrue(true);
	}
}