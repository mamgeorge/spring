package com.basics.repository;

import com.basics.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.basics.util.UtilityMain.LOGGER;
import static com.basics.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityRepositoryTest {

	@Resource private CityRepository cityRepository;

	@BeforeAll public void setUp() throws Exception { }

	@Test public void testing() {
		//
		String txtLine = "testing";
		LOGGER.info(PAR + txtLine);
		assertTrue(txtLine.equals("testing"));
	}

	// @Test 
	public void findAll() {
		//
		String txtLines = "";
		Iterable<City> iterable = cityRepository.findAll();
		List<City> cities = new ArrayList<City>();
		for (City city : iterable) {
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