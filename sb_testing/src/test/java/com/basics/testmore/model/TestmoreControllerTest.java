package com.basics.testmore.model;

import com.basics.testmore.controller.TestmoreController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Objects;

import static com.basics.testmore.util.UtilityMain.LOGGER;
import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@WebMvcTest(TestmoreController.class)
@SpringBootTest
public class TestmoreControllerTest {

	@Autowired private TestmoreController testmoreController;

	// @BeforeAll public void setUp( ) throws Exception { }

	@Test public void root() {
		//
		String txtLines = "";
		ModelAndView modelAndView = this.testmoreController.root();
		View view = modelAndView.getView();
		txtLines += PAR + "root! [ " + modelAndView + " ]";
		txtLines += PAR + "root! CONTEXT_PATH: " + testmoreController.getContextPath();
		txtLines += PAR + "root! MAV viewName: " + modelAndView.getViewName();
		txtLines += PAR + "root! view content: " + view;
		LOGGER.info(txtLines);
		//
		// assertTrue( testmoreController.getContextPath( ).contains( "/basics" ) ) ;
		assertNotNull(this.testmoreController);
		assertTrue(Objects.requireNonNull(modelAndView.getViewName()).contains("index"));
	}

	@Test public void getCities() {
		//
		String txtLines = "";
		ModelAndView modelAndView = this.testmoreController.getCities();
		ModelMap modelMap = modelAndView.getModelMap();
		ArrayList<?> cityList = (ArrayList) modelMap.get("cityList"); // ArrayList<City>
		City[] cities = new City[cityList.size()];
		cities = (City[]) cityList.toArray(cities);
		City city = cities[0];
		String name = city.getName();
		StringBuffer stringBuffer = new StringBuffer();
		modelMap.forEach((key, val) -> stringBuffer.append(PAR).append(key).append(": ").append(val));
		//
		txtLines += PAR + "showCities! [ " + modelAndView + " ]";
		txtLines += PAR + "showCities! MAV viewName: " + modelAndView.getViewName();
		txtLines += PAR + "showCities! model map [ " + modelMap + " ]";
		txtLines += PAR + "showCities! model buffer [ " + stringBuffer + " ]";
		txtLines += PAR + "showCities! model cityList [ " + cityList.size() + " ]";
		txtLines += PAR + "showCities! model cities [ " + cities.length + " ]";
		txtLines += PAR + "showCities! model city [ " + city + " ]";
		txtLines += PAR + "showCities! model name [ " + name + " ]";
		LOGGER.info(txtLines);
		assertTrue(Objects.requireNonNull(modelAndView.getViewName()).contains("showCities"));
	}

	@Test public void showTimer() {
		//
		String txtLines = this.testmoreController.showTimer();
		LOGGER.info(PAR + "showTimer! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test public void showUtils() {
		//
		String txtLines = this.testmoreController.showUtils().substring(0, 10);
		LOGGER.info(PAR + "showUtils! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test public void exits() {
		LOGGER.info(PAR + "exits! ");
	}
}
