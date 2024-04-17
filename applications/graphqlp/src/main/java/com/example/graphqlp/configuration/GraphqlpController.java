package com.example.graphqlp.configuration;

import com.example.graphqlp.persistence.City;
import com.example.graphqlp.persistence.CityService;
import com.example.graphqlp.persistence.Country;
import com.example.graphqlp.persistence.CountryService;
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

import static com.example.graphqlp.persistence.Country.ISO_CODES;

@RestController
public class GraphqlpController {

	private final CountryService countryService;
	private final CityService cityService;
	private final Random random = new Random();

	@Autowired
	public GraphqlpController(CountryService countryService,  CityService cityService) {
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
		Integer cityId = random.nextInt(intMax+10);
		System.out.println("cityId: " + cityId);

		City city = cityService.findById(cityId);
		responseEntity = new ResponseEntity<>(city, HttpStatus.OK);
		return responseEntity;
	}
}
