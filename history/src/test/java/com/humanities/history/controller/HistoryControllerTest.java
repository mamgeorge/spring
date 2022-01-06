package com.humanities.history.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistoryControllerTest {

	@Autowired private IHistoryService historyService;
	public static final String FRMT = "\t%-30s %s\n";

	@BeforeAll void setup() {}

	@Test void root() {
		//
		String txtLines = "";
		Model model = null;
		//
		HistoryController historyController = new HistoryController();
		ModelAndView modelAndView = historyController.root(model);
		View view = modelAndView.getView();
		txtLines += String.format(FRMT, "historyController", historyController);
		txtLines += String.format(FRMT, "modelAndView", modelAndView);
		txtLines += String.format(FRMT, "getViewName", modelAndView.getViewName());
		txtLines += String.format(FRMT, "view", view);
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	@Test void showHistory() {
		//
		String txtLines = "";
		//
		History history = historyService.findById(1L);
		txtLines += String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(history.getDatebeg().contains("0004"));
	}
}
