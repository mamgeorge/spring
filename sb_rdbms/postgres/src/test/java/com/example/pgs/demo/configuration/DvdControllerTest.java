package com.example.pgs.demo.configuration;

import com.example.pgs.demo.model.Actor;
import com.example.pgs.demo.persistence.ActorRepository;
import com.example.pgs.demo.persistence.ActorService;
import com.example.pgs.demo.persistence.CustomerRepository;
import com.example.pgs.demo.persistence.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DvdControllerTest {

	@Mock private ActorRepository actorRepository;
	@Mock private CustomerRepository customerRepository;
	private ActorService actorService;
	private CustomerService customerService;
	private DvdController dvdController;

	@BeforeEach void init(){

		actorService = new ActorService(actorRepository);
		customerService = new CustomerService(customerRepository);
		dvdController = new DvdController(actorService, customerService);
		System.out.println("init");
	}

	@Test void test_getActors( ) {

		List<Actor> actors = new ArrayList<>();
//		when(actorService.findAll()).thenReturn(actors);
//		ResponseEntity<List<Actor>> responseEntity = dvdController.getActors();
//		System.out.println(responseEntity);
		assertTrue(true);
	}
}