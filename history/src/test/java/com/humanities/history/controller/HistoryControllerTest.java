package com.humanities.history.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistoryControllerTest {

	@Autowired private HistoryController historyController;
	@Autowired private IHistoryService historyService;
	public static final String FRMT = "\t%-5s %s\n";

	@BeforeAll void setup() {
		//
		System.out.println("陕西, 西安");
	}

	@Test void getSample() {
		//
		History history = History.getSample();
		String txtLines = String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(history.getDatebeg().contains("begi"));
	}

	@Test void showHistory() {
		//
		History history = historyService.findById(1L);
		String txtLines = String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(history.getDatebeg().contains("00"));
	}

	//############
	@Test void root() {
		//
		String txtLines = "";
		//
		ModelAndView MAV = historyController.root();
		View view = MAV.getView();
		txtLines += String.format(FRMT, "historyController", historyController);
		txtLines += String.format(FRMT, "modelAndView", MAV);
		txtLines += String.format(FRMT, "getViewName", MAV.getViewName());
		txtLines += String.format(FRMT, "view", view);
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void listing() {
		//
		String txtLines = "";
		//
		ModelAndView MAV = historyController.listing();
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		List<History> histories = (List<History>) hashMap.get("histories");
		AtomicInteger ai = new AtomicInteger();
		for (History history : histories) {
			txtLines += String.format(FRMT, ai.incrementAndGet(), history.getPersonname());
		}
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void inputs() {
		//
		ModelAndView MAV = historyController.inputs("5");
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		History history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void posted() {
		//
		History history = History.getSample();
		ModelAndView MAV = historyController.posted(history,"Submit");
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}
}
