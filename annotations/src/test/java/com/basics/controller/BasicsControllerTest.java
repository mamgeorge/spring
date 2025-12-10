package com.basics.controller;

import com.basics.model.City;
import com.basics.services.CityService;
import com.basics.services.ICityService;
import com.basics.util.UtilityMain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.ArrayList;

import static com.basics.util.UtilityMain.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@WebMvcTest(BasicsController.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BasicsControllerTest {

	@Autowired
	private CityService cityService;

	@Autowired
	private BasicsController basicsController;

	@BeforeAll
	public void setUp() throws Exception { }

	@Test
	public void root() {
		//
		String txtLines = "";
		Model model = null;
		ModelAndView modelAndView = this.basicsController.root(null);
		View view = modelAndView.getView();
		txtLines += PAR + "root! [ " + modelAndView + " ]";
		txtLines += PAR + "root! CONTEXT_PATH: " + basicsController.getContextPath();
		txtLines += PAR + "root! MAV viewName: " + modelAndView.getViewName();
		txtLines += PAR + "root! view content: " + view;
		LOGGER.info(txtLines);
		assertThat(this.basicsController).isNotNull();
		//	assertTrue( basicsController.getContextPath( ).contains( "/basics" ) ) ;
		assertTrue(modelAndView.getViewName().contains("index"));
	}

	@Test
	public void showEntity() {
		//
		String FRMT = "\t%-15s %s\n";
		ResponseEntity responseEntity = this.basicsController.showEntity();
		//
		String txtLines = UtilityMain.exposeObject(responseEntity);
		txtLines += String.format(FRMT, "toString", responseEntity);
		txtLines += String.format(FRMT, "getHeaders", responseEntity.getHeaders());
		txtLines += String.format(FRMT, "getBody", responseEntity.getBody());
		txtLines += String.format(FRMT, "getStatusCode", responseEntity.getStatusCode());

		LOGGER.info(PAR + "showEntity! " + txtLines);
		assertNotNull(responseEntity);
	}

	@Test
	public void showCities() {
		//
		String txtLines = "";
		ReflectionTestUtils.setField(basicsController, "cityService", cityService);
		ModelAndView modelAndView = this.basicsController.showCities();
		ModelMap modelMap = modelAndView.getModelMap();
		//
		@SuppressWarnings("unchecked")
		ArrayList<City> arrayList = (ArrayList<City>) modelMap.get("cities");
		City[] cities = new City[arrayList.size()];
		cities = arrayList.toArray(cities);
		City city = cities[0];
		String name = city.getName();
		StringBuilder stringBuilder = new StringBuilder();
		modelMap.forEach((key, val) -> {
			stringBuilder.append(CRLF + key + ": " + val);
		});
		//
		txtLines += CRLF + "showCities! [ " + modelAndView + " ]";
		txtLines += CRLF + "showCities! MAV viewName: " + modelAndView.getViewName();
		txtLines += CRLF + "showCities! model map [ " + modelMap + " ]";
		txtLines += CRLF + "showCities! model buffer [ " + stringBuilder + " ]";
		txtLines += CRLF + "showCities! model arrayList [ " + arrayList.size() + " ]";
		txtLines += CRLF + "showCities! model cities [ " + cities.length + " ]";
		txtLines += CRLF + "showCities! model city [ " + city + " ]";
		txtLines += CRLF + "showCities! model name [ " + name + " ]";
		System.out.println(txtLines);
		System.out.println(stringBuilder);
		assertTrue(modelAndView.getViewName().contains("showCities"));
	}

	@Test
	public void showTimer() {
		//
		String txtLines = this.basicsController.showTimer();
		LOGGER.info(PAR + "showTimer! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test
	public void showUtils() {
		//
		String txtLines = this.basicsController.showUtils().substring(0, 10);
		LOGGER.info(PAR + "showUtils! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test
	public void exits() throws Exception {
		LOGGER.info(PAR + "exits! ");
	}
}
