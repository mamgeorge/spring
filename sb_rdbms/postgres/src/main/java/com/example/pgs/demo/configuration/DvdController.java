package com.example.pgs.demo.configuration;

import com.example.pgs.demo.model.Actor;
import com.example.pgs.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

	@GetMapping( "/getCustomers" )
	public ResponseEntity<List<Customer>> getCustomers( ) {

		ResponseEntity<List<Customer>> responseEntity;
		List<Customer> customer = customerService.findAll();
		responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
		return responseEntity;
	}
}
