package com.example.pgs.demo.configuration;

import com.example.pgs.demo.model.Actor;
import com.example.pgs.demo.model.Customer;
import com.example.pgs.demo.persistence.ActorService;
import com.example.pgs.demo.persistence.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

// https://mkyong.com/spring-boot/spring-boot-spring-data-jpa-postgresql/
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
public class DvdController {

	private final ActorService actorService;
	private final CustomerService customerService;
	private final Random random = new Random();

	public DvdController(ActorService actorService, CustomerService customerService) {
		this.actorService = actorService;
		this.customerService = customerService;
	}

	@GetMapping( { "/", "/root", "/home", "/index" } )
	public ModelAndView home( ) {
		return new ModelAndView("index");
	}

	@GetMapping( { "/timer" } )
	public ResponseEntity<String> timer( ) {
		ResponseEntity<String> responseEntity;
		String timer  = Instant.now().toString();
		responseEntity = new ResponseEntity<>(timer, HttpStatus.OK);
		return responseEntity;
	}

	// json Actors
	@GetMapping( "/getActors" )
	public ResponseEntity<List<Actor>> getActors( ) {

		ResponseEntity<List<Actor>> responseEntity;
		List<Actor> actors = actorService.findAll();
		responseEntity = new ResponseEntity<>(actors, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getActor/{id}" )
	public ResponseEntity<Actor> getActor(@PathVariable String id) {

		ResponseEntity<Actor> responseEntity;
		Actor actor = actorService.findAll().get(Integer.parseInt(id));
		responseEntity = new ResponseEntity<>(actor, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getActorRnd" )
	public ResponseEntity<Actor> getActorRnd( ) {

		ResponseEntity<Actor> responseEntity;
		long maxId = actorService.getMaxId();
		Long randomLongId = (long) random.nextInt((int) maxId) + 1;
		System.out.printf("maxId: %s, randomLongId: %s %n", maxId, randomLongId);
		Actor actor = actorService.findById(randomLongId);
		responseEntity = new ResponseEntity<>(actor, HttpStatus.OK);
		return responseEntity;
	}

	// json Customers
	@GetMapping( "/getCustomers" )
	public ResponseEntity<List<Customer>> getCustomers( ) {

		ResponseEntity<List<Customer>> responseEntity;
		List<Customer> customers = customerService.findAll();
		responseEntity = new ResponseEntity<>(customers, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getCustomer/{id}" )
	public ResponseEntity<Customer> getCustomer(@PathVariable int id) {

		ResponseEntity<Customer> responseEntity;
		Customer customer = customerService.findById(id);
		responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getCustomerRnd" )
	public ResponseEntity<Customer> getCustomerRnd( ) {

		ResponseEntity<Customer> responseEntity;
		long maxId = customerService.getMaxId();
		int randId = random.nextInt((int) maxId) + 1;
		System.out.printf("maxId: %s, randomLongId: %s %n", maxId, randId);
		Customer customer = customerService.findById(randId);
		responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
		return responseEntity;
	}

	// HTML
	@GetMapping( "/showCustomers" )
	public ModelAndView showCustomers( ) {

		List<Customer> customers = customerService.findAll();
		ModelAndView mView = new ModelAndView("showCustomers");
		mView.addObject("customers", customers);
		return mView;
	}

	@GetMapping( "/showCustomer/{id}" )
	public ModelAndView showCustomer(@PathVariable int id) {

		Customer customer = customerService.findById(id);
		ModelAndView mView = new ModelAndView("showCustomer");
		mView.addObject("customer", customer);
		return mView;
	}

	@GetMapping( "/showCustomerRnd" )
	public ModelAndView showCustomerRnd( ) {

		long maxId = customerService.getMaxId();
		int rndId = random.nextInt((int) maxId) + 1;
		System.out.printf("maxId: %s, randomLongId: %s %n", maxId, rndId);
		Customer customer = customerService.findById(rndId);
		ModelAndView mView = new ModelAndView("showCustomer");
		mView.addObject("customer", customer);
		return mView;
	}

	// BOOTSTRAP
	@GetMapping( "/bootCustomers" )
	public ModelAndView bootCustomers( ) {

		List<Customer> customers = customerService.findAll();
		ModelAndView mView = new ModelAndView("bootCustomers");
		mView.addObject("customers", customers);
		return mView;
	}

	@GetMapping( "/bootCustomer/{id}" )
	public ModelAndView bootCustomer(@PathVariable int id) {

		Customer customer = customerService.findById(id);
		ModelAndView mView = new ModelAndView("bootCustomer");
		mView.addObject("customer", customer);
		return mView;
	}

	@GetMapping( "/bootCustomerRnd" )
	public ModelAndView bootCustomerRnd( ) {

		long maxId = customerService.getMaxId();
		int rndId = random.nextInt((int) maxId) + 1;
		System.out.printf("maxId: %s, randomLongId: %s %n", maxId, rndId);
		Customer customer = customerService.findById(rndId);
		ModelAndView mView = new ModelAndView("bootCustomer");
		mView.addObject("customer", customer);
		return mView;
	}

	public static String getJson(Object object) {

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
