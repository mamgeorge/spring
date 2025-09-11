package com.example.graphqld.configuration;

import com.example.graphqld.persistence.Actor;
import com.example.graphqld.persistence.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;

@RestController
public class GqlClientController {

	private final ActorService actorService;

	@Autowired public GqlClientController(ActorService actorService) {
		this.actorService = actorService;
	}

	@GetMapping( { "/", "/home", "/root" } ) public ModelAndView root( ) {

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		ModelAndView MAV = new ModelAndView("index");
		MAV.addObject("timer", timer);
		return MAV;
	}

	@GetMapping( "/actorsAll" ) public Actor[] getActors() {

		System.out.println("getActors");
		Actor[] actors = actorService.getActors();
		return actors;
	}

	@GetMapping( "/actorById/{id}" ) public Actor getActorById(@PathVariable Integer id) {

		System.out.println("actorById: " + id);
		Actor actor = actorService.getActorbyId(id);
		return actor;
	}

	@GetMapping( "/actorByIdVar/{id}" ) public Actor getActorbyIdVar(@PathVariable Integer id) {

		System.out.println("actorByIdVar: " + id);
		Actor actor = actorService.getActorbyIdVar(id);
		return actor;
	}

	@GetMapping( "/objectById/{id}" ) public Object getObjectById(@PathVariable Integer id) {

		System.out.println("objectById: " + id);
		Object object = actorService.getObjectbyId(id);
		return object;
	}
}
