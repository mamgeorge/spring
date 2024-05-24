package com.example.embedded.configuration;

import com.example.embedded.model.City;
import com.example.embedded.model.CityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@RestController
public class EmbeddedController {

	private final CityRepository cityRepository;
	private final ApplicationContext applicationContext; //added for close

	@Autowired EmbeddedController(CityRepository cityRepository, ApplicationContext applicationContext) {
		this.cityRepository = cityRepository;
		this.applicationContext = applicationContext;
	}

	private static final Random random = new Random();

	@GetMapping( { "/", "/root", "/home", "/index" } )
	public ModelAndView home( ) {

		System.out.println(Instant.now());
		return new ModelAndView("index", new HashMap<>());
	}

	@GetMapping( "/showCities" ) public ModelAndView showCities( ) {

		List<City> cities = cityRepository.findAll();

		ModelAndView modelAndView = new ModelAndView("cities");
		modelAndView.addObject("cities", cities);
		return modelAndView;
	}

	@GetMapping( "/showCityRnd" ) public ModelAndView showCityRnd( ) {

		long maxId = cityRepository.count();
		long rndId = random.nextLong(maxId) + 1;
		System.out.println("rndId: " + rndId);
		City city = cityRepository.findById(rndId).get();

		ModelAndView modelAndView = new ModelAndView("city");
		modelAndView.addObject("city", city);
		return modelAndView;
	}

	@GetMapping( "/showCity/{cityId}" ) public ModelAndView showCity(
		@PathVariable String cityId) {

		Long longId = Long.parseLong(cityId);
		City city = cityRepository.getById(longId);

		ModelAndView modelAndView = new ModelAndView("city");
		modelAndView.addObject("city", city);
		return modelAndView;
	}

	// @ApiResponses()
	@GetMapping( "/showCity" ) public City showCity( ) {

		long maxId = cityRepository.count();
		long rndId = random.nextLong(maxId) + 1;
		System.out.println("rndId: " + rndId);
		return cityRepository.findById(rndId).get();
	}

	@GetMapping( "/exit" ) public void exit( ) {

		System.out.println("EXIT");
		SpringApplication.exit(applicationContext);
		System.exit(0);
	}

	// utils
	public static String formatObject(Object object) {

		String json = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(object); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return json;
	}
}
