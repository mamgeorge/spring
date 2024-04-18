package com.example.graphqlp.configuration;

import com.example.graphqlp.persistence.Actor;
import com.example.graphqlp.persistence.ActorService;
import com.example.graphqlp.persistence.Address;
import com.example.graphqlp.persistence.AddressService;
import com.example.graphqlp.persistence.City;
import com.example.graphqlp.persistence.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Controller
public class GraphqlpController {

	private final AddressService addressService;
	private final ActorService actorService;
	private final CityService cityService;
	private final Random random = new Random();

	@Autowired
	public GraphqlpController(AddressService addressService,  ActorService actorService,
		CityService cityService ) {
		this.addressService = addressService;
		this.actorService = actorService;
		this.cityService = cityService;
	}

	@GetMapping ({"/", "/home", "/root" })
	public ModelAndView root(){

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		ModelAndView MAV  = new ModelAndView("index");
		return MAV;
	}

	@QueryMapping public List<Address> getAddresses(@Argument int count) {

		List<Address> addresses = addressService.findAll();
		return addresses;
	}

	@QueryMapping public Address getAddress(@Argument int addressId) {

		Address address = addressService.findById(addressId);
		return address;
	}

	//############
	@GetMapping( "/getActors" ) public ResponseEntity<List<Actor>> getActors( ) {

		ResponseEntity<List<Actor>> responseEntity;
		List<Actor> actors = actorService.findAll();
		System.out.println("actors: " + actors);

		responseEntity = new ResponseEntity<>(actors, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getActorRnd" ) public ResponseEntity<Actor> getActorRnd( ) {

		ResponseEntity<Actor> responseEntity;
		int intMax = (int) actorService.getMaxId() + 1;
		Integer actorId = random.nextInt(intMax);
		System.out.println("actorId: " + actorId);

		Actor actor = actorService.findById(actorId);
		responseEntity = new ResponseEntity<>(actor, HttpStatus.OK);
		return responseEntity;
	}

	//############
	@GetMapping( "/getAddresses" ) public ResponseEntity<List<Address>> getAddresses( ) {

		ResponseEntity<List<Address>> responseEntity;
		List<Address> addresses = addressService.findAll();
		responseEntity = new ResponseEntity<>(addresses, HttpStatus.OK);
		return responseEntity;
	}

	@ResponseBody @GetMapping( "/getAddressRnd" ) public ResponseEntity<Address> getCountryRnd( ) {

		ResponseEntity<Address> responseEntity;
		int intMax = (int) addressService.getMaxId() + 1;
		Integer addressId = random.nextInt(intMax);
		System.out.println("addressId: " + addressId);

		Address address = addressService.findById(addressId);
		responseEntity = new ResponseEntity<>(address, HttpStatus.OK);
		return responseEntity;
	}

	//############
	@GetMapping( "/getCities" ) public ResponseEntity<List<City>> getCities( ) {

		ResponseEntity<List<City>> responseEntity;
		List<City> cities = cityService.findAll();
		System.out.println("cities: " + cities);

		responseEntity = new ResponseEntity<>(cities, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getCityRnd" ) public ResponseEntity<City> getCityRnd( ) {

		ResponseEntity<City> responseEntity;
		int intMax = (int) cityService.getMaxId() + 1;
		Integer cityId = random.nextInt(intMax);
		System.out.println("cityId: " + cityId);

		City city = cityService.findById(cityId);
		responseEntity = new ResponseEntity<>(city, HttpStatus.OK);
		return responseEntity;
	}

}
