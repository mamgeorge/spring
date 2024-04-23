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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.RESET_CONTENT;

@Controller
public class GraphqlpController {

	private final AddressService addressService;
	private final ActorService actorService;
	private final CityService cityService;
	private final Random random = new Random();

	@Autowired public GraphqlpController(AddressService addressService,
		CityService cityService, ActorService actorService) {

		this.addressService = addressService;
		this.cityService = cityService;
		this.actorService = actorService;
	}

	@GetMapping( { "/", "/home", "/root" } )
	public ModelAndView root( ) {

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		ModelAndView MAV = new ModelAndView("index");
		return MAV;
	}

	//#### graphql ####
	@QueryMapping public Address addressById(@Argument int address_id) {

		Address address = addressService.findById(address_id);
		return address;
	}
	@QueryMapping public City cityById(@Argument int city_id) {

		City city = cityService.findById(city_id);
		return city;
	}
	@QueryMapping public Actor actorById(@Argument int actor_id) {

		Actor actor = actorService.findById(actor_id);
		return actor;
	}

	@QueryMapping public List<Address> getAddresses(@Argument int count) {

		List<Address> addresses = addressService.findAll().subList(0, count);
		return addresses;
	}
	@QueryMapping public List<City> getCities(@Argument int count) {

		List<City> cities = cityService.findAll().subList(0, count);
		return cities;
	}
	@QueryMapping public List<Actor> getActors(@Argument int count) {

		List<Actor> actors = actorService.findAll().subList(0, count);
		return actors;
	}

	@SchemaMapping public City city(Address address) {

		City city = cityService.findById(address.getCity_id());
		return city;
	}

	@QueryMapping public List<Address> getAddressRng(@Argument int beg, @Argument int end) {

		List<Address> addresses = addressService.findAll().subList(beg, end);
		return addresses;
	}

	@PostMapping( "/putActor" ) public ResponseEntity<Actor> putActor(@RequestBody Actor actor) {

		ResponseEntity<Actor> responseEntity;
		if ( actor == null ) {
			actor = new Actor();
		} else {
			actor = actorService.save(actor);
		}
		responseEntity = new ResponseEntity<>(actor, OK);
		return responseEntity;
	}

	//#### regular REST ####
	@GetMapping( "/getAddresses" ) @ResponseBody public List<Address> getAddresses( ) {

		List<Address> addresses = addressService.findAll();
		return addresses;
	}
	@GetMapping( "/getCities" ) @ResponseBody public List<City> getCities( ) {

		List<City> cities = cityService.findAll();
		return cities;
	}
	@GetMapping( "/getActors" ) public ResponseEntity<List<Actor>> getActors( ) {

		ResponseEntity<List<Actor>> responseEntity;
		List<Actor> actors = actorService.findAll();
		responseEntity = new ResponseEntity<>(actors, OK);
		return responseEntity;
	}

	@GetMapping( "/getActorsCnt/{count}" ) @ResponseBody public List<Actor> getActorsCnt(@PathVariable int count) {
		List<Actor> actors = actorService.findAll().subList(0, count);
		return actors;
	}

	@GetMapping( "/getAddressRnd" ) @ResponseBody public Address getAddressRnd( ) {

		int intMax = (int) addressService.getMaxId() + 1;
		Integer address_id = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", address_id: " + address_id);

		Address address = addressService.findById(address_id);
		return address;
	}
	@GetMapping( "/getCityRnd" ) @ResponseBody  public City getCityRnd( ) {

		int intMax = (int) cityService.getMaxId() + 1;
		Integer city_id = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", city_id: " + city_id);

		City city = cityService.findById(city_id);
		return city;
	}
	@GetMapping( "/getActorRnd" )@ResponseBody public Actor getActorRnd( ) {

		int intMax = (int) actorService.getMaxId() + 1;
		Integer actor_id = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", actor_id: " + actor_id);

		Actor actor = actorService.findById(actor_id);
		return actor;
	}

	//#### regular MVC ####
	@GetMapping( "/showActors" ) public ModelAndView showActors( ) {

		ArrayList<Actor> actors = (ArrayList<Actor>) actorService.findAll();
		actors.sort((o1, o2) -> o1.getActor_id().compareTo(o2.getActor_id()));

		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("actorsList");
		MAV.addObject("actors", actors);
		return MAV;
	}

	@GetMapping( "/showActor/{actor_id}" ) public ModelAndView showActor(@PathVariable int actor_id) {

		Actor actor = actorService.findById(actor_id);

		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("actorShow");
		MAV.addObject("actor", actor);
		return MAV;
	}

	@GetMapping( "/showActorRnd" ) public ModelAndView showActorRnd( ) {

		int intMax = (int) actorService.getMaxId() + 1;
		int actor_id = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", actor_id: " + actor_id);
		Actor actor = actorService.findById(actor_id);

		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("actorShow");
		MAV.addObject("actor", actor);
		return MAV;
	}

	@PostMapping( "/showActorMod" ) public ModelAndView showActorMod(@ModelAttribute Actor actor,
		@RequestParam String action) {

		HttpStatus httpStatus = NO_CONTENT;
		if ( action.equals("reset") ) {
			actor = new Actor();
			actor.setLast_update(Timestamp.from(Instant.now()));
			httpStatus = RESET_CONTENT;
			//
		} else if ( action.equals("save") ) {
			actor = actorService.save(actor);
			httpStatus = CREATED;
			//
		} else if ( action.equals("delete") ) {
			int actor_id = actor.getActor_id();
			if ( actor_id > 200 ) {
				httpStatus = actorService.delete(actor_id);
				actor = new Actor();
				actor.setLast_update(Timestamp.from(Instant.now()));
			} else {
				httpStatus = NOT_MODIFIED;
			}
		}

		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("actorShow");
		MAV.addObject("actor", actor);
		MAV.addObject("httpStatus", httpStatus);
		return MAV;
	}
}
