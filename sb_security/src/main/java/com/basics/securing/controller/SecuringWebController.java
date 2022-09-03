package com.basics.securing.controller; // .controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

// @RestController = @Controller + @ResponseBody
@Controller
public class SecuringWebController {

	private static final String RETURN = "<br /><a href = '/securing/home'>return</a>";

	// @GetMapping = @RequestMapping( value = {"/"}, method = RequestMApping.GET )
	@RequestMapping( value = { "/roots" }, method = RequestMethod.GET ) @ResponseBody
	public String roots( ) {
		//
		String txtLines = Instant.now().toString() + RETURN;
		System.out.println(txtLines);
		return txtLines;
	}

	@GetMapping( { "/", "/home"} ) public String home( ) { return "home"; }

	@GetMapping( "/hello" ) public String hello( ) { return "hello"; }

	@GetMapping( "/login" ) public String login( ) { return "login"; }

	@GetMapping( "/times" ) @ResponseBody
	public String times( ) { return Instant.now().toString() + RETURN; }
}