package com.example.embedded.configuration;

import com.example.embedded.model.City;
import com.example.embedded.model.CityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

import static com.example.embedded.configuration.EmbeddedController.formatObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// https://spring.io/guides/gs/testing-web/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class EmbeddedControllerTests {

	public static final Logger LOGGER = Logger.getLogger(EmbeddedControllerTests.class.getName());

	@Autowired private CityRepository cityRepository;
	@Autowired private ApplicationContext applicationContext;
	@Autowired private EmbeddedController sbController = null;

	private static final String EOL = "\n";

	@BeforeAll void setup() {

		sbController = new EmbeddedController(cityRepository, applicationContext);
	}

	@Test void test_contextLoads() {

		LOGGER.info("sbController: " + sbController.toString());
		assertNotNull(sbController);
	}

	@Test void test_home() {

		ModelAndView MAV = sbController.home();
		System.out.println("sbController.root(): " + MAV.getViewName());
		assertNotNull(MAV);
	}

	@Test void test_showCities() {

		ModelAndView MAV = sbController.showCities();
		List<City> cities = (List<City>) MAV.getModel().get("cities");
		System.out.println(cities);
		assertNotNull(MAV);
	}

	@Test void test_showCityRnd() {

		ModelAndView MAV = sbController.showCityRnd();
		City city = (City) MAV.getModel().get("city");
		System.out.println(formatObject(city));
		assertNotNull(MAV);
	}

	@Test void test_showCity_parm( ) {

		City city = sbController.showCity(5);
		System.out.println(formatObject(city));
		assertNotNull(city);
	}

	@Test void test_showCity( ) {

		City city = sbController.showCity();
		System.out.println(formatObject(city));
		assertNotNull(city);
	}
}