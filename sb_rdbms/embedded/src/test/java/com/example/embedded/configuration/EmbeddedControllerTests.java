package com.example.embedded.configuration;

import com.example.embedded.model.City;
import com.example.embedded.model.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.example.embedded.configuration.EmbeddedController.formatObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// https://spring.io/guides/gs/testing-web/
class EmbeddedControllerTests {

	public static final Logger LOGGER = Logger.getLogger(EmbeddedControllerTests.class.getName());
	private static final String EOL = "\n";

	private EmbeddedController controllerEmb = null;

	// statics
	public static City getCity() {

		City city = new City();
		city.setId(1L);
		city.setName("Columbus");
		city.setPopulation(750000);
		city.setCreated(new Date(System.currentTimeMillis()));
		city.setUpdated(new Timestamp(System.currentTimeMillis()));
		return city;
	}

	@BeforeEach
	void setup() {

		City city = getCity();
		List<City> cities = new ArrayList<>();
		cities.add(city);

		Page<City> page = new PageImpl(cities);

		CityRepository cityRepository = mock(CityRepository.class);
		when(cityRepository.count()).thenReturn(10L);
		when(cityRepository.findAll()).thenReturn(cities);
		when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
		when(cityRepository.getById(anyLong())).thenReturn(city);
		when(cityRepository.findAll(any(Pageable.class))).thenReturn(page);

		ApplicationContext applicationContext = mock(ApplicationContext.class);

		controllerEmb = new EmbeddedController(cityRepository, applicationContext);
	}

	@Test
	void contextLoads() {

		LOGGER.info("controllerEmb: " + controllerEmb.toString());
		assertNotNull(controllerEmb);
	}

	@Test
	void home() {

		ModelAndView MAV = controllerEmb.home();
		System.out.println("controllerEmb.root(): " + MAV.getViewName());
		assertNotNull(MAV);
	}

	@Test
	void showCities() {

		ModelAndView MAV = controllerEmb.showCities(5);
		List<City> cities = (List<City>) MAV.getModel().get("cities");
		System.out.println(formatObject(cities));
		assertNotNull(MAV);
	}

	@Test
	void showCityRnd() {

		ModelAndView MAV = controllerEmb.showCityRnd();
		City city = (City) MAV.getModel().get("city");
		System.out.println(formatObject(city));
		assertNotNull(MAV);
	}

	@Test
	void showCityNum() {

		ModelAndView MAV = controllerEmb.showCityNum(5);
		City city = (City) MAV.getModel().get("city");
		System.out.println(formatObject(city));
		assertNotNull(city);
	}

	// @ApiResponses()
	@Test
	void jsonCities() {

		List<City> cities = controllerEmb.jsonCities(5);
		System.out.println(formatObject(cities));
		assertNotNull(cities);
	}

	@Test
	void jsonCityRnd() {

		City city = controllerEmb.jsonCityRnd();
		System.out.println(formatObject(city));
		assertNotNull(city);
	}

	@Test
	void jsonCityNum() {

		City city = controllerEmb.jsonCityNum(5);
		System.out.println(formatObject(city));
		assertNotNull(city);
	}
}