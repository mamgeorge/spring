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

// https://mkyong.com/spring-boot/spring-boot-spring-data-jpa-postgresql/
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
public class DvdController {

	private final ActorService actorService;
	private final CustomerService customerService;

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

	@GetMapping( "/getCustomers" )
	public ResponseEntity<List<Customer>> getCustomers() {

		ResponseEntity<List<Customer>> responseEntity;
		List<Customer> customers = customerService.findAll();
		responseEntity = new ResponseEntity<>(customers, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/getCustomer/{id}" )
	public ResponseEntity<Customer> getCustomer(@PathVariable String id) {

		ResponseEntity<Customer> responseEntity;
		Customer customer = customerService.findAll().get(Integer.parseInt(id));
		responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping( "/showCustomers" )
	public ModelAndView showCustomers() {

		List<Customer> customers = customerService.findAll();
		ModelAndView MAV = new ModelAndView("seeCustomers");
		MAV.addObject("customers", customers);
		return MAV;
	}

	@GetMapping( "/showCustomer/{id}" )
	public ModelAndView showCustomer(@PathVariable int id) {

		Customer customer = customerService.findAll().get(id);
		ModelAndView MAV = new ModelAndView("seeCustomer");
		MAV.addObject("customer", customer);
		return MAV;
	}
}
