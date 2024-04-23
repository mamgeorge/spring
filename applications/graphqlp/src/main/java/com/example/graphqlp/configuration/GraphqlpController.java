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
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
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

	@QueryMapping public List<Address> getAddressRng(@Argument int beg, @Argument int end) {

		List<Address> addresses = addressService.findAll().subList(beg, end);
		return addresses;
	}

	@SchemaMapping public City city(Address address) {

		City city = cityService.findById(address.getCity_id());
		return city;
	}

	@QueryMapping public City cityById(@Argument int city_id) {

		City city = cityService.findById(city_id);
		return city;
	}

	@QueryMapping public List<City> getCities(@Argument int count) {

		List<City> cities = cityService.findAll().subList(0, count);
		return cities;
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
	@GetMapping( "/getActors" ) public ResponseEntity<List<Actor>> getActors( ) {

		ResponseEntity<List<Actor>> responseEntity;
		List<Actor> actors = actorService.findAll();
		responseEntity = new ResponseEntity<>(actors, OK);
		return responseEntity;
	}

	@GetMapping( "/getActorsCnt/{count}" )
	public ResponseEntity<List<Actor>> getActorsCnt(@PathVariable int count) {

		ResponseEntity<List<Actor>> responseEntity;
		List<Actor> actors = actorService.findAll().subList(0, count);
		responseEntity = new ResponseEntity<>(actors, OK);
		return responseEntity;
	}

	@GetMapping( "/getActorRnd" ) public ResponseEntity<Actor> getActorRnd( ) {

		ResponseEntity<Actor> responseEntity;
		int intMax = (int) actorService.getMaxId() + 1;
		Integer actor_id = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", actor_id: " + actor_id);

		Actor actor = actorService.findById(actor_id);
		responseEntity = new ResponseEntity<>(actor, OK);
		return responseEntity;
	}

	//#### regular MVC ####
	@GetMapping( "/showActors" ) public ModelAndView showActors( ) {

		List<Actor> actors = actorService.findAll();

		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("actorsShow");
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
