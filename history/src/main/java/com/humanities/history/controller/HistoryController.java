package com.humanities.history.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.logging.Logger;

import java.util.HashMap;

@RestController
public class HistoryController {

	private static final Logger LOGGER = Logger.getLogger(HistoryController.class.getName());
	private static final String RETURN_LINK = "<br /><a href = '/' >return</a><br />";

	@GetMapping({"/", "/index", "/home"})
	public ModelAndView root(Model model) {
		//

		System.out.println("index");
		LOGGER.info("index");
		ModelAndView MAV = new ModelAndView("index", new HashMap<>());
		return MAV;
	}
}
