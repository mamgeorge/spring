package com.basics.securing.controller; // .controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

// https://www.javavogue.com/2020/04/how-to-read-value-from-application-properties-in-spring-boot/
// https://stackabuse.com/how-to-access-property-file-values-in-spring-boot/
// @RestController = @Controller + @ResponseBody
@Controller
public class SecuringWebController {

	@Value( "${server.servlet.context-path}" ) private String CONTEXT_PATH;

	private String getReturn( ) { return "<br /><a href = '" + CONTEXT_PATH + "'>return</a>"; }

	// @GetMapping = @RequestMapping( value = {"/roots"}, method = RequestMapping.GET )
	@GetMapping @ResponseBody
	public String roots( ) {
		//
		String txtLines = Instant.now().toString() + getReturn();
		System.out.println(txtLines);
		return txtLines;
	}

	@GetMapping( { "/", "/home" } ) public String home( ) { return "home"; }

	@GetMapping( "/hello" ) public String hello( ) { return "hello"; }

	@GetMapping( "/login" ) public String login( ) { return "login"; }

	@GetMapping( "/times" ) @ResponseBody
	public String times( ) { return Instant.now().toString() + getReturn(); }
}
