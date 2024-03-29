package com.camunda.academy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericTests {

	public static final Logger LOGGER = Logger.getLogger(GenericTests.class.getName());

	@Test void logger_test( ) {

		LOGGER.severe("severe");
		LOGGER.warning("warning");
		LOGGER.info("info");
		LOGGER.fine("fine");
		assertTrue(true);
	}

	@Test void prettyPrintDefault_test( ) {

		String txtLines = "";
		String json = "{'a':1,'b':'test'}".replaceAll("'", "\"");
		System.out.println(json);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Object jsonObject = objectMapper.readValue(json, Object.class);
			txtLines = objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(jsonObject);
		}
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void prettyPrintGlobal_test( ) {

		String txtLines = "";
		String json = "{'a':1,'b':'test'}".replaceAll("'", "\"");
		System.out.println(json);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(INDENT_OUTPUT);
			Object jsonObject = objectMapper.readValue(json, Object.class);
			txtLines = objectMapper.writeValueAsString(jsonObject);
		}
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(txtLines);
		assertTrue(true);
	}
}
