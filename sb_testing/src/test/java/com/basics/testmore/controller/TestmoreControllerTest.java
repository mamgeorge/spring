package com.basics.testmore.controller;

import com.basics.testmore.model.City;
import com.basics.testmore.model.Country;
import com.basics.testmore.util.UtilityMain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.testmore.util.UtilityMain.LOGGER;
import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

//@WebMvcTest(TestmoreController.class)
@SpringBootTest
public class TestmoreControllerTest {

	@Autowired private TestmoreController testmoreController;

	// @BeforeAll public void setUp( ) throws Exception { }

	@Test public void root( ) {

		String txtLines = "";
		ModelAndView modelAndView = this.testmoreController.root();
		txtLines = PAR + "CONTEXT_PATH: " + testmoreController.getContextPath();
		txtLines += PAR + "viewName: " + modelAndView.getViewName();
		System.out.println(txtLines);

		assertNotNull(this.testmoreController);
		assertTrue(Objects.requireNonNull(modelAndView.getViewName()).contains("index"));
	}

	@Test public void authenticate( ) {

		String txtLines = "";
		ModelAndView modelAndView = this.testmoreController.authenticate();
		txtLines = PAR + "CONTEXT_PATH: " + testmoreController.getContextPath();
		txtLines += PAR + "viewName: " + modelAndView.getViewName();
		System.out.println(txtLines);

		assertNotNull(this.testmoreController);
		assertTrue(Objects.requireNonNull(modelAndView.getViewName()).contains("login"));
	}

	@Test public void getCities( ) {

		String txtLines = "";
		ModelAndView modelAndView = this.testmoreController.getCities();
		ModelMap modelMap = modelAndView.getModelMap();
		ArrayList<?> cityList = (ArrayList) modelMap.get("cityList"); // ArrayList<City>
		City[] cities = new City[cityList.size()];
		cities = cityList.toArray(cities);
		City city = cities[0];
		String name = city.getName();
		StringBuffer stringBuffer = new StringBuffer();
		modelMap.forEach((key, val) -> stringBuffer.append(PAR).append(key).append(": ").append(val));

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

	@Test public void getCityIds( ) {

		ModelAndView modelAndView = this.testmoreController.getCityIds("1");
		ModelMap modelMap = modelAndView.getModelMap();
		City city = (City) modelMap.get("city"); // ArrayList<City>
		StringBuffer stringBuffer = new StringBuffer();
		modelMap.forEach((key, val) -> stringBuffer.append(PAR).append(key).append(": ").append(val));

		System.out.println(stringBuffer);
		System.out.println(UtilityMain.exposeObject(city));
		assertNotNull(city);
	}

	@Test public void getCityPth( ) {

		ModelAndView modelAndView = this.testmoreController.getCityPth("1");
		ModelMap modelMap = modelAndView.getModelMap();
		City city = (City) modelMap.get("city"); // ArrayList<City>
		StringBuffer stringBuffer = new StringBuffer();
		modelMap.forEach((key, val) -> stringBuffer.append(PAR).append(key).append(": ").append(val));

		System.out.println(stringBuffer);
		System.out.println(UtilityMain.exposeObject(city));
		assertNotNull(city);
	}

	@Test void getCityBean( ) {

		ResponseEntity<String> responseEntity = this.testmoreController.getCityBean();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
			String txtLines = objectWriter.writeValueAsString(responseEntity);
			System.out.println("responseEntity: " + txtLines);
		}
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println("responseEntity: " + responseEntity.toString());
		assertNotNull(responseEntity);
	}

	@Test void getCountries( ) {

		ModelAndView modelAndView = this.testmoreController.getCountries();
		ModelMap modelMap = modelAndView.getModelMap();
		List<Country> subCountries = (List<Country>) modelMap.get("countryList");

		AtomicInteger ai = new AtomicInteger();
		subCountries.forEach(country -> System.out.printf("\t %02d %s\n", ai.incrementAndGet(), country));
		assertNotNull(subCountries);
	}

	@Test void getCountryIds( ) {

		ModelAndView modelAndView = this.testmoreController.getCountryIds("1");
		ModelMap modelMap = modelAndView.getModelMap();
		Country country = (Country) modelMap.get("country");
		StringBuffer stringBuffer = new StringBuffer();
		modelMap.forEach((key, val) -> stringBuffer.append(PAR).append(key).append(": ").append(val));

		System.out.println(stringBuffer);
		System.out.println(UtilityMain.exposeObject(country));
		assertNotNull(country);
	}

	@Test void getCountryPth( ) {

		ResponseEntity<String> responseEntity = (ResponseEntity) this.testmoreController.getCountryPth(1L);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
			String txtLines = objectWriter.writeValueAsString(responseEntity);
			System.out.println("responseEntity: " + txtLines);
		}
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println("responseEntity: " + responseEntity.toString());
		assertNotNull(responseEntity);
	}

	@Test public void showTimer( ) {

		String txtLines = this.testmoreController.showTimer();
		LOGGER.info(PAR + "showTimer! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test public void showUtils( ) {

		String txtLines = this.testmoreController.showUtils().substring(0, 10);
		LOGGER.info(PAR + "showUtils! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test @Disabled public void exits( ) {

		System.out.println("BEFORE A SHUTDOWN");
		testmoreController.exits();
		System.out.println("DONE");
	}

	@Test public void handleError( ) {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ModelAndView modelAndView = testmoreController.handleError(request);
		System.out.println("modelAndView: " + modelAndView);
		assertNotNull(modelAndView);
	}
}
