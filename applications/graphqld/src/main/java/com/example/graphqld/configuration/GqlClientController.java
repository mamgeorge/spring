package com.example.graphqld.configuration;

import com.example.graphqld.persistence.Actor;
import com.example.graphqld.persistence.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

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

	@GetMapping( "/actorById/{id}" ) public Actor getActorById(@PathVariable Integer id) {

		Actor actor = actorService.getActorbyId(id);
		return actor;
	}

	@GetMapping( "/actorByIdVar/{id}" ) public Actor getActorbyIdVar(@PathVariable Integer id) {

		Actor actor = actorService.getActorbyIdVar(id);
		return actor;
	}

	@GetMapping( "/objectById/{id}" ) public Object getObjectById(@PathVariable Integer id) {

		Object object = actorService.getObjectbyId(id);
		return object;
	}
}
