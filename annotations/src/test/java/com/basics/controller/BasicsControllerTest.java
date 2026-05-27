package com.basics.controller;

import com.basics.model.City;
import com.basics.services.CityService;
import com.basics.util.UtilityMain;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;

import static com.basics.util.UtilityMain.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static wiremock.org.eclipse.jetty.http.HttpStatus.OK_200;

//@WebMvcTest(BasicsController.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BasicsControllerTest {

	@Autowired
	private CityService cityService;

	@Autowired
	private BasicsController basicsController;

	@BeforeAll
	public void setUp() { }

	@Test
	public void root() {
		//
		String txtLines = "";
		ModelAndView modelAndView = this.basicsController.root();
		View view = modelAndView.getView();
		txtLines += PAR + "root! [ " + modelAndView + " ]";
		txtLines += PAR + "root! CONTEXT_PATH: " + basicsController.getContextPath();
		txtLines += PAR + "root! MAV viewName: " + modelAndView.getViewName();
		txtLines += PAR + "root! view content: " + view;
		LOGGER.info(txtLines);
		assertThat(this.basicsController).isNotNull();
		//	assertTrue( basicsController.getContextPath( ).contains( "/basics" ) ) ;
		assertTrue(Objects.requireNonNull(modelAndView.getViewName()).contains("index"));
	}

	@Test
	public void showEntity() {
		//
		String FRMT = "\t%-15s %s\n";
		ResponseEntity<MultiValueMap<String,String>> responseEntity
				= this.basicsController.showEntity();
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
		modelMap.forEach((key, val) ->
				stringBuilder.append(CRLF).append(key).append(": ").append(val));
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
		assertTrue(Objects.requireNonNull(
				modelAndView.getViewName()).contains("showCities"));
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
		assertTrue(true);
	}

	@Test
	public void ping() {

		String result = basicsController.ping();
		LOGGER.info(result);
		assertNotNull(result);
	}

	@Test void wireMockTest() {

		String result = "ping";
		int port = 8090;
		String url = "/ping";

		// 1 start server
		WireMockServer wireMockServer = new WireMockServer(port);
		wireMockServer.start();
		WireMock.configureFor("localhost", wireMockServer.port());
		wireMockServer.stubFor(get(urlEqualTo(url))
			.willReturn(aResponse()
			.withStatus(OK_200)
			.withHeader(CONTENT_TYPE, TEXT_PLAIN_VALUE)
			.withBody(result)));

		// 2 run request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("http://localhost:" + port + url))
			.build();

		// 3 get response
		HttpResponse<String> response = null;
		try { response = client.send(request, HttpResponse.BodyHandlers.ofString()); }
		catch(IOException | InterruptedException ex ) { System.out.println( "ERROR: " + ex.getMessage() ); }

		// 4 validate
		System.out.println("response: " + response);
		assert(Objects.requireNonNull(response).body().equals(result));
		assert(response.statusCode()==200);

		// 5 stop server
		wireMockServer.stop();
	}
}
