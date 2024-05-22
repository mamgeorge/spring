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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.aspectj.util.LangUtil.EOL;

@RestController
public class EmbeddedController {

	private final CityRepository cityRepository;
	private final ApplicationContext applicationContext; //added for close

	@Autowired EmbeddedController(CityRepository cityRepository, ApplicationContext applicationContext) {
		this.cityRepository = cityRepository;
		this.applicationContext = applicationContext;
	}

	public static final String DLM = "\t";
	private static final int MAX_DISPLAY = 20;
	private static final Random random = new Random();

	private static final String FRMT = "<pre>\t%02d %-15s %9d</pre>\n";
	private static final String HEADER = "<h3>SbController</h3>";
	private static final String RETURN = "<br /><a href = '/home'>return</a>";

	@GetMapping( { "/", "/root", "/home", "/index" } )
	public ModelAndView home( ) {

		System.out.println(Instant.now());
		return new ModelAndView("index", new HashMap<>());
	}

	@GetMapping( "/showCities" ) public String showCities( ) {

		System.out.println("showCities");
		List<City> cities = cityRepository.findAll();
		List<City> subCities = new ArrayList<>(cities.subList(0, MAX_DISPLAY));

		StringBuilder stringBuilder = new StringBuilder();
		subCities.forEach(city -> stringBuilder
			.append(String.format(FRMT, city.getId(), city.getName(), city.getPopulation())));
		return HEADER + stringBuilder + RETURN;
	}

	// @ApiResponses()
	@GetMapping( "/showCityRnd" ) public City showCityRnd( ) {

		long maxId = cityRepository.count();
		long rndId = random.nextLong(maxId);
		City city = cityRepository.findById(rndId).get();
		System.out.println("showCity: " + rndId + EOL + formatObject(city));
		return city;
	}

	@GetMapping( "/showCity/{cityId}" ) public ModelAndView showCity(
		@PathVariable String cityId) {

		// id is normal; id.get() used with Optional
		System.out.println("showCity/" + cityId);
		Long longId = Long.parseLong(cityId);
		City city = cityRepository.getById(longId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("city", city);
		modelAndView.setViewName("city");
		return modelAndView;
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
