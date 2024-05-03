package com.example.graphqld.configuration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

@RestController
public class GraphqldController {

	@GetMapping( { "/", "/home", "/root" } )
	public ModelAndView root( ) {

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		ModelAndView MAV = new ModelAndView("index");
		MAV.addObject("timer", timer);
		return MAV;
	}
}
