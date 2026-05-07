package com.example.embedded.configuration;

import com.example.embedded.model.City;
import com.example.embedded.model.CityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@Tag(name = "Cities", description = "Show Cities")
@RestController
public class EmbeddedController {

	private static final Random random = new Random();
	private final CityRepository cityRepository;
	private final ApplicationContext applicationContext; //added for close

	/* annotations

		@Tag			for controller to group related operations
		@Schema			for model structure, constraints, and examples of req/res objects (was @ApiModelProperty)
		@Operation		for methods as a summary of endpoint
		@Parameter		describes request parameters (query, path, header, cookie)
		@RequestBody	describes body in POST/PUT operations
		@ApiResponse	describes potential responses (200 OK, 404 Not Found)
	*/
	@Autowired
	EmbeddedController(CityRepository cityRepository, ApplicationContext applicationContext) {
		this.cityRepository = cityRepository;
		this.applicationContext = applicationContext;
	}

	//#### views
	@GetMapping({"/", "/root", "/home", "/index"})
	public ModelAndView home() {

		System.out.println(Instant.now());
		return new ModelAndView("index", new HashMap<>());
	}

	@GetMapping("/showCities/{num}")
	public ModelAndView showCities(@Parameter(description = "number to return") @PathVariable int num) {

		long count = cityRepository.count();
		int pageNumber = 0;
		if (num > count) { num = (int) count; }
		if (num < 1) { num = 1; }

		Sort sort = Sort.by("name").ascending();
		Pageable pageable = PageRequest.of(pageNumber, num, sort);

		// Execute the call
		Page<City> page = cityRepository.findAll(pageable);

		List<City> cities = page.toList();

		ModelAndView modelAndView = new ModelAndView("cities");
		modelAndView.addObject("cities", cities);
		return modelAndView;
	}

	@GetMapping("/showCityRnd")
	public ModelAndView showCityRnd() {

		long maxId = cityRepository.count();
		long rndId = random.nextLong(maxId) + 1;
		System.out.println("rndId: " + rndId);
		City city = cityRepository.findById(rndId).get();

		ModelAndView modelAndView = new ModelAndView("city");
		modelAndView.addObject("city", city);
		return modelAndView;
	}

	@GetMapping("/showCityNum/{id}")
	public ModelAndView showCityNum(@PathVariable long id) {

		System.out.println("id: " + id);
		City city = cityRepository.findById((id)).get();

		ModelAndView modelAndView = new ModelAndView("city");
		modelAndView.addObject("city", city);
		return modelAndView;
	}

	//#### APIs
	@Operation(summary = "Get Timestamp", description = "Returns ISO Timestamp")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200", description = "Time returned",
			content = @Content( mediaType = "application/json",
			schema = @Schema(implementation = String.class))),
		@ApiResponse(responseCode = "404", description = "Time not found", content = @Content)
	})
	@GetMapping("/getTime") public ResponseEntity<String> getTime() {

		ResponseEntity<String> response = null;
		String time = Instant.now().toString();
		System.out.println("time: " + time);
		response = ResponseEntity.ok(time);
		return response;
	}

	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
	@GetMapping("/jsonCities/{intid}")
	public List<City> jsonCities(@PathVariable int intid) {

		long longcount = cityRepository.count();
		int pageNumber = 0;
		if (intid > longcount) { intid = (int) longcount; }
		if (intid < 1) { intid = 1; }

		Sort sort = Sort.by("name").ascending();
		Pageable pageable = PageRequest.of(pageNumber, intid, sort);

		// Execute the call
		Page<City> page = cityRepository.findAll(pageable);

		List<City> cities = page.toList();
		return cities;
	}

	@Operation(summary = "grab random city", description = "grabs any random city")
	@GetMapping("/jsonCityRnd")
	public City jsonCityRnd() {

		long maxId = cityRepository.count();
		long longid = random.nextLong(maxId) + 1;
		System.out.println("longid: " + longid);
		return cityRepository.findById(longid).get();
	}

	@GetMapping("/jsonCityNum/{longid}")
	public City jsonCityNum(@PathVariable long longid) {

		System.out.println("longid: " + longid);
		City city = cityRepository.getById(longid);
		return city;
	}

	//#### utils
	@GetMapping("/exit")
	private void exit() {

		System.out.println("EXIT");
		SpringApplication.exit(applicationContext);
		System.exit(0);
	}

	public static String formatObject(Object object) {

		String json = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		try {
			json = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return json;
	}

}
