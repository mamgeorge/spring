package com.humanities.history.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistoryControllerTest {

	@Autowired private HistoryController historyController;
	@Autowired private IHistoryService historyService;
	private static final String FRMT = "\t%-5s %s\n";
	private static final String HOST_EXT = "https://httpbin.org/";
	private static final String ENCODED = "历史 | &#21382;&#21490; | \u5386\u53f2 | \\u5386\\u53f2";

	@BeforeAll void setup() {
		//
		System.out.println(ENCODED);
	}

	//############
	@Test void sample_HttpCient() {
		//
		String txtLines = "";
		String url = HOST_EXT + "get?id=1234";
		//
		HttpResponse<String> httpResponse = sample_HttpCient(url);
		txtLines += String.format(FRMT, "statusCode", httpResponse.statusCode());
		txtLines += httpResponse.body().replaceAll("\\s+", " ");
		//
		System.out.println(txtLines);
		assertNotNull(httpResponse);
	}

	@Test void history_getSample() {
		//
		History history = History.getSample();
		String txtLines = String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(history.getDatebeg().contains("begi"));
	}

	@Test void history_showHistory() {
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
		ModelAndView MAV = historyController.posted(history, "Submit");
		HashMap<String, Object> hashMap = (HashMap<String, Object>) MAV.getModel();
		history = (History) hashMap.get("history");
		String txtLines = String.format(FRMT, "getPersonname:", history.getPersonname());
		//
		System.out.println(txtLines);
		assertNotNull(historyController);
	}

	//############
	private static HttpResponse<String> sample_HttpCient(String url) {
		//
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(url))
				.setHeader(USER_AGENT, "Java11Client Bot")
				.build();
		HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
		HttpResponse<String> httpResponse = null;
		try {
			HttpResponse.BodyHandler<String> bodyHandlers = HttpResponse.BodyHandlers.ofString();
			httpResponse = httpClient.send(httpRequest, bodyHandlers);
		} catch (IOException | InterruptedException ex) {System.out.println("ERROR: " + ex.getMessage());}
		//
		return httpResponse;
	}
}
