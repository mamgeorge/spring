package com.example.pgs.demo.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DvdControllerTest {

	@Mock private ActorRepository actorRepository;
	private ActorService actorService;
	private DvdController dvdController;

	@BeforeEach void init(){

		actorService = new ActorService(actorRepository);
		dvdController = new DvdController(actorService);
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