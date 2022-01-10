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
	private static final String ENCODED = "历史 | &#21382;&#21490; | \u5386\u53f2 | \\u5386\\u53f2";
	private static final String RETURN = "<br /><a href = '/' >return</a>";
	private static final int MAX_DISPLAY = 20;
	private static final int SAMPLE_ITEM = 1;

	@GetMapping({"/", "/index", "/home"}) public ModelAndView root() {
		//
		System.out.println("index");
		LOGGER.info("index");
		ModelAndView MAV = new ModelAndView("index", new HashMap<>());
		return MAV;
	}

	@GetMapping("/listing") public ModelAndView listing() {
		//
		List<History> histories = historyService.findAll();
		System.out.println("histories: " + histories.size());
		if (histories.size() > MAX_DISPLAY) {histories = histories.subList(0, 20);}
		//
		HashMap<String, List<History>> hashMap = new HashMap<>();
		hashMap.put("histories", histories);
		histories.stream().forEach(hst -> System.out.println(hst.showHistory()));
		//
		ModelAndView MAV = new ModelAndView("listing", hashMap);
		return MAV;
	}

	@GetMapping("/inputs") public ModelAndView inputs() {
		//
		History history = History.getSample();
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());
		MAV.addObject("blurb", ENCODED);
		//
		System.out.println("history: " + history.showHistory());
		return MAV;
	}

	@GetMapping("/inputs/{id}") public ModelAndView inputs(@PathVariable String id) {
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
		try {history = historyService.findById(longId);} catch (NoSuchElementException ex) {
			LOGGER.severe(ex.getMessage());
		}
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());
		MAV.addObject("blurb", ENCODED);
		//
		System.out.println("history: " + history.showHistory());
		return MAV;
	}

	@PostMapping("/posted") // ModelAttribute or RequestBody?
	public ModelAndView posted(@ModelAttribute History history, @RequestParam("nav") String nav) {
		//
		ModelAndView MAV = new ModelAndView();
		if (history == null || history.getId() == null) {
			history = History.getSample();
			String msg = "WARNING! HISTORY OBJECT WAS EMPTY!";
			System.out.println(
				"\n" + "#".repeat(msg.length()) + "\n" + msg + "\n" + "#".repeat(msg.length()) + "\n");
		}
		Long longId = history.getId();
		if (nav != null && nav.equals("back")) {
			--longId;
			MAV = traverse(history, longId);
		}
		if (nav != null && nav.equals("frwd")) {
			++longId;
			MAV = traverse(history, longId);
		}
		if (nav != null && nav.equals("list")) {MAV = listing();}
		if (nav != null && nav.equals("clear")) {MAV = inputs();}
		if (nav != null && nav.equals("save")) {MAV = saver(history);}
		//
		return MAV;
	}

	@PostMapping("/traverse") public ModelAndView traverse(@ModelAttribute History history, Long longId) {
		//
		try {history = historyService.findById(longId);} catch (NoSuchElementException ex) {
			LOGGER.info(ex.getMessage());
		}
		System.out.println("history: " + history.showHistory());
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());
		MAV.addObject("blurb", ENCODED);
		return MAV;
	}

	@PostMapping("/saver") public ModelAndView saver(@ModelAttribute History history) {
		//
		// 	@PostMapping(path = "/posted",
		// 	consumes = {APPLICATION_FORM_URLENCODED_VALUE}, produces = { APPLICATION_FORM_URLENCODED_VALUE})
		try {history = historyService.save(history);} catch (NoSuchElementException ex) {
			LOGGER.info(ex.getMessage());
		}
		System.out.println("history: " + history.showHistory());
		//
		ModelAndView MAV = new ModelAndView();
		MAV.setViewName("inputs");
		MAV.addObject("history", history);
		MAV.addObject("historySum", history.showHistory());
		MAV.addObject("blurb", ENCODED);
		return MAV;
	}

	@GetMapping("/errors") public void errors() {
		// throw new Exception("Exception!");
		throw new RuntimeException("RuntimeException!");
	}
}
