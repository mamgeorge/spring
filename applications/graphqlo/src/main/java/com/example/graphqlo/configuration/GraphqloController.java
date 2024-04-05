package com.example.graphqlo.configuration;

import com.example.graphqlo.persistence.City;
import com.example.graphqlo.persistence.CityService;
import com.example.graphqlo.persistence.Country;
import com.example.graphqlo.persistence.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import static com.example.graphqlo.persistence.Country.ISO_CODES;

@RestController
public class GraphqloController {

	private final CountryService countryService;
	private final CityService cityService;
	private final Random random = new Random();

	@Autowired
	public GraphqloController(CountryService countryService,  CityService cityService) {
		this.countryService = countryService;
		this.cityService = cityService;
	}

	@GetMapping ({"/", "/home", "/root" })
	public ModelAndView root(){

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		ModelAndView MAV  = new ModelAndView("index");
		return MAV;
	}

	@GetMapping( "/getCountries" )
	public ResponseEntity<List<Country>> getCountries( ) {

		ResponseEntity<List<Country>> responseEntity;
		List<Country> countries = countryService.findAll();
		responseEntity = new ResponseEntity<>(countries, HttpStatus.OK);
		return responseEntity;
	}

	@ResponseBody
	@GetMapping( "/getCountryRnd" )
	public ResponseEntity<Country> getCountryRnd( ) {

		ResponseEntity<Country> responseEntity;
		int intRnd = random.nextInt(ISO_CODES.length) + 1;
		String isoCodeId = ISO_CODES[intRnd];
		System.out.println("isoCodeId: " + isoCodeId);

		Country country = countryService.findById(isoCodeId);
		responseEntity = new ResponseEntity<>(country, HttpStatus.OK);
		return responseEntity;
	}

	//############
	@GetMapping( "/getCities" )
	public ResponseEntity<List<City>> getCities( ) {

		ResponseEntity<List<City>> responseEntity;
		List<City> cities = cityService.findAll();
		System.out.println("cities: " + cities);

		responseEntity = new ResponseEntity<>(cities, HttpStatus.OK);
		return responseEntity;
	}

	// @ResponseBody
	@GetMapping( "/getCityRnd" )
	public ResponseEntity<City> getCityRnd( ) {

		ResponseEntity<City> responseEntity;
		int intMax = (int) cityService.getMaxId() + 1;
		Integer intId = random.nextInt(intMax) ;
		System.out.println("intId: " + intId);

		City city = cityService.findById(intId);
		responseEntity = new ResponseEntity<>(city, HttpStatus.OK);
		return responseEntity;
	}
}
