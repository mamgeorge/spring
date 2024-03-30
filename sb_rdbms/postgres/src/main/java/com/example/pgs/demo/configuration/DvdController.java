package com.example.pgs.demo.configuration;

import com.example.pgs.demo.model.Actor;
import com.example.pgs.demo.model.Customer;
import com.example.pgs.demo.persistence.ActorService;
import com.example.pgs.demo.persistence.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;

// https://mkyong.com/spring-boot/spring-boot-spring-data-jpa-postgresql/
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
public class DvdController {

	private final ActorService actorService;
	private final CustomerService customerService;
	private final Random random = new Random();

	@Autowired
	public DvdController(ActorService actorService, CustomerService customerService) {
		this.actorService = actorService;
		this.customerService = customerService;
	}

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

	//
	@GetMapping( "/getCustomers" )
	public ResponseEntity<List<Customer>> getCustomers( ) {

		ResponseEntity<List<Customer>> responseEntity;
		List<Customer> customers = customerService.findAll();
		responseEntity = new ResponseEntity<>(customers, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getCustomer/{id}" )
	public ResponseEntity<Customer> getCustomer(@PathVariable long id) {

		ResponseEntity<Customer> responseEntity;
		Customer customer = customerService.findById(id);
		responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/showCustomers" )
	public ModelAndView showCustomers( ) {

		List<Customer> customers = customerService.findAll();
		ModelAndView mView = new ModelAndView("seeCustomers");
		mView.addObject("customers", customers);
		return mView;
	}

	@GetMapping( "/showCustomer/{id}" )
	public ModelAndView showCustomer(@PathVariable long id) {

		Customer customer = customerService.findById(id);
		ModelAndView mView = new ModelAndView("seeCustomer");
		mView.addObject("customer", customer);
		return mView;
	}

	@GetMapping( "/showCustomerRnd" )
	public ModelAndView showCustomerRnd( ) {

		long maxId = customerService.getMaxId();
		Long randomLongId = (long) random.nextInt((int) maxId) + 1;
		System.out.printf("maxId: %s, randomLongId: %s %n", maxId, randomLongId);
		Customer customer = customerService.findById(randomLongId);
		ModelAndView mView = new ModelAndView("seeCustomer");
		mView.addObject("customer", customer);
		return mView;
	}
}
