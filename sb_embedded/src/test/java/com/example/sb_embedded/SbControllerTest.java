package com.example.sb_embedded;

import java.io.StringWriter;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://spring.io/guides/gs/testing-web/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) @AutoConfigureMockMvc
class SbControllerTest {

	public static final Logger LOGGER = Logger.getLogger(SbControllerTest.class.getName());


	@Autowired private SbController sbController;
	@Autowired private TestRestTemplate restTemplate;
	@Autowired private MockMvc mockMvc;
	@LocalServerPort int PORT;

	private static final String ASSERTION = "ASSERTION";
	private static final String EOL = "\n";
	private static final String TAB = "\t";
	private static final String LOCALHOST = "http://localhost:";

	@Test void test_contextLoads() {
		//
		System.out.println(EOL + "test_contextLoads()");
		LOGGER.info(TAB + "sbController: " + sbController.toString());
		Assert.isTrue(sbController != null, ASSERTION);
	}

	@Test void test_root() {
		//
		System.out.println(EOL + "test_root()");
		String txtReturn = sbController.root();
		System.out.println(TAB + "sbController.root(): " + txtReturn);
		Assert.isTrue(txtReturn.contains("home"), ASSERTION);
	}

	@Test void test_root_RT() {
		//
		System.out.println(EOL + "test_root_RT()");
		String txtLines = "";
		String html = this.restTemplate.getForObject(LOCALHOST + PORT + "/", String.class);
		txtLines += TAB + "PORT: " + PORT + EOL;
		txtLines += TAB + "html: " + html;
		System.out.println(txtLines);
		Assert.isTrue(html.contains("home"), ASSERTION);
	}

	@Test void test_root_MM() throws Exception {
		//
		System.out.println(EOL + "test_root_MM()");
		StringWriter stringWriter = new StringWriter();
		stringWriter.append("####");
		// OutputStream outputStream = System.out;
		//
		ResultActions resultActions = this.mockMvc.perform(get("/"))
			.andDo(print(stringWriter))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("home")));
		System.out.println(TAB + "RAS: " + resultActions + EOL);
		//System.out.println(TAB + "RAO: " + resultActions.andDo(print(outputStream)) );
	}

	@Test void test_home() {
		//
		System.out.println(EOL + "test_home()");
		ModelAndView MAV = sbController.home();
		System.out.println(TAB + "sbController.root(): " + MAV.getViewName());
		Assert.isTrue(MAV.getViewName().equals("index"), ASSERTION);
	}

	@Test void test_showCities() {
		//
		System.out.println(EOL + "test_showCities()");
		String txtLines = "";
		String[] cities = sbController.showCities().split(EOL);
		txtLines += cities[0] + " / " + "len: " + cities.length;
		System.out.println("txtLines: " + txtLines);
		Assert.isTrue(cities.length > 20, ASSERTION);
	}

	@Test void test_showCity() {
		//
		System.out.println(EOL + "test_showCity()");
		City city = sbController.showCity();
		String txtLines = city.toString();
		System.out.println("txtLines: " + txtLines);
		System.out.println("city.getName(): " + city.getName() );
		Assert.isTrue(city.getName().equals("CN Chongqing"), ASSERTION);
	}

	@Test void test_showCity_val() {
		//
		System.out.println(EOL + "test_showCity_val()");
		String html = this.restTemplate.getForObject(LOCALHOST + PORT + "/showCity/5", String.class);
		System.out.println("html: " + html.toString());
		Assert.isTrue(html.contains("MexicoCity"), ASSERTION);
	}
}