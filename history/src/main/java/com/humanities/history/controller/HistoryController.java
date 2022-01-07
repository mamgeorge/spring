package com.humanities.history.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
public class HistoryController {

	@Autowired private IHistoryService historyService;
	private static final Logger LOGGER = Logger.getLogger(HistoryController.class.getName());
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 1;

	@GetMapping({"/", "/index", "/home"})
	public ModelAndView root() {
		//
		System.out.println("index");
		LOGGER.info("index");
		ModelAndView MAV = new ModelAndView("index", new HashMap<>());
		return MAV;
	}

	@GetMapping({"/listing"})
	public ModelAndView listing() {
		//
		List<History> histories = historyService.findAll();
		System.out.println("histories: " + histories.size());
		//
		HashMap<String, List<History>> hashMap = new HashMap<>();
		hashMap.put("histories", histories);
		ModelAndView MAV = new ModelAndView("listing", hashMap);
		return MAV;
	}

	@GetMapping("/inputs/{id}")
	public ModelAndView inputs(@PathVariable String id) {
		//
		System.out.println("inputs: [" + id + "]");
		Long longId = null;
		try {longId = Long.parseLong(id);} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
			longId = Long.valueOf(SAMPLE_ITEM);
		}
		if (longId < 1) {longId = 0L;}
		//
		History history = new History();
		try {history = historyService.findById(longId);}
		catch (NoSuchElementException ex) { LOGGER.severe(ex.getMessage()); }
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		//
		System.out.println("history: " + history.showHistory());
		return MAV;
	}

	@PostMapping("/posted")
	public ModelAndView posted(@ModelAttribute History history, @RequestParam("nav") String nav) {
		//
		Long longId = history.getId();
		if (nav != null && nav.equals("back")) {--longId;}
		if (nav != null && nav.equals("frwd")) {++longId;}
		if (nav==null || nav.equals("Submit")) {longId = 0L;}
		try {history = historyService.findById(longId);}
		catch (NoSuchElementException ex) { LOGGER.severe(ex.getMessage());	}
		System.out.println("nav: " + nav + " / history: " + history.showHistory());
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		return MAV;
	}
}
