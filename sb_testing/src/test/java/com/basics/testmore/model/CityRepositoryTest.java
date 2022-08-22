package com.basics.testmore.model;

import com.basics.testmore.repository.CityRepository;
import com.basics.testmore.util.UtilityMain;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.LOGGER;
import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @RunWith(SpringRunner.class)
public class CityRepositoryTest {

	@Resource private CityRepository cityRepository;

	// @BeforeAll public void setUp( ) throws Exception { }

	@Test public void testSettersGetters( ) {
		//
		City city = new City();

		System.out.println(UtilityMain.exposeObject(city));
		assertNotNull(city);
	}

	@Test public void equals( ) {
		//
		City city1 = new City();
		City city2 = new City();
		boolean isEqual = city1.equals(city2);

		System.out.println("isEqual: " +isEqual);
		assertTrue(isEqual);
	}

	// @Test 
	public void findAll( ) {
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
	}
}