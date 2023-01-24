package com.basics.controller;

import com.basics.model.City;
import com.basics.util.UtilityMain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.basics.util.UtilityMain.LOGGER;
import static com.basics.util.UtilityMain.PAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@WebMvcTest(BasicsController.class)
@SpringBootTest
@TestInstance( Lifecycle.PER_CLASS )
public class BasicsControllerTest {

	@Autowired private BasicsController basicsController;

	@BeforeAll public void setUp( ) throws Exception { }

	@Test public void root( ) {
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

	@Test public void showEntity( ) {
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

	@Test public void showCities( ) {
		//
		String txtLines = "";
		ModelAndView modelAndView = this.basicsController.showCities();
		ModelMap modelMap = modelAndView.getModelMap();
		//
		@SuppressWarnings( "unchecked" )
		ArrayList<City> arrayList = (ArrayList<City>) modelMap.get("cities");
		City[] cities = new City[arrayList.size()];
		cities = arrayList.toArray(cities);
		City city = cities[0];
		String name = city.getName();
		StringBuffer stringBuffer = new StringBuffer();
		modelMap.forEach((key, val) -> { stringBuffer.append(PAR + key + ": " + val); });
		//
		txtLines += PAR + "showCities! [ " + modelAndView + " ]";
		txtLines += PAR + "showCities! MAV viewName: " + modelAndView.getViewName();
		txtLines += PAR + "showCities! model map [ " + modelMap + " ]";
		txtLines += PAR + "showCities! model buffer [ " + stringBuffer + " ]";
		txtLines += PAR + "showCities! model arrayList [ " + arrayList.size() + " ]";
		txtLines += PAR + "showCities! model cities [ " + cities.length + " ]";
		txtLines += PAR + "showCities! model city [ " + city + " ]";
		txtLines += PAR + "showCities! model name [ " + name + " ]";
		LOGGER.info(txtLines);
		assertTrue(modelAndView.getViewName().contains("showCities"));
	}

	@Test public void showTimer( ) {
		//
		String txtLines = this.basicsController.showTimer();
		LOGGER.info(PAR + "showTimer! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test public void showUtils( ) {
		//
		String txtLines = this.basicsController.showUtils().substring(0, 10);
		LOGGER.info(PAR + "showUtils! " + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test public void exits( ) throws Exception { LOGGER.info(PAR + "exits! "); }
}
