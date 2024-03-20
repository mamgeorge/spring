package com.database.dbase;

import com.database.dbase.history.History;
import com.database.dbase.history.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class RdbmsController {

	// @Autowired private IHistoryService cityService;
	@Autowired private HistoryRepository historyRepository;

	private static final String RETURN = "<br /><a href = '/'>return</a>";
	private static final int MAX_DISPLAY = 20;

	@GetMapping( "/root" )
	public String root( ) {
		//
		System.out.println("root");
		return Instant.now() + RETURN;
	}

	@GetMapping( { "/", "/home" } )
	public ModelAndView home( ) {
		//
		System.out.println("home");
		ModelAndView MAV = new ModelAndView();
		MAV.addObject(new HashMap());
		MAV.setViewName("home");
		return MAV;
	}

	@GetMapping( { "/read" } )
	public @ResponseBody Iterable<History> read( ) {
		//
		System.out.println("read");
		String txtLines = "";
		Iterable<History> histories = historyRepository.findAll();
		return histories;
	}

	@GetMapping( { "/showHistories" } )
	public ModelAndView showHistories( ) {
		//
		System.out.println("showHistories");
		Iterable<History> iterable = historyRepository.findAll();
		List<History> histories = StreamSupport
			.stream(iterable.spliterator(), false)
			.collect(Collectors.toList());
		System.out.println("histories: " + histories);
		//
		ModelAndView MAV = new ModelAndView();
		MAV.addObject("histories", histories);
		MAV.setViewName("histories");
		return MAV;
	}
}