package com.example.graphqld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import graphql.com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.fasterxml.jackson.core.util.DefaultIndenter.SYS_LF;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GqlClientAppTests {

	public static final String EOL = "\n";
	public static final String DLM = "\t";
	String URL = "http://localhost:9090/graphql";
	String[] URLs = {
		"http://ip.jsontest.com",
		"https://dummyjson.com/user/2",
		"https://dummyjson.com/users",
		"https://dummyjson.com/product/2",
		"https://dummyjson.com/products",
		"https://jsonplaceholder.typicode.com/posts/2",
		"https://jsonplaceholder.typicode.com/posts"
	};

	@Autowired ApplicationContext context;

	@Test void context_test( ) {

		// GenericApplicationContext context = new AnnotationConfigApplicationContext();
		Environment environment = context.getEnvironment();
		String serverPort = environment.getProperty("local.server.port");
		String activeProfiles = Arrays.toString(environment.getActiveProfiles());
		String config = environment.getProperty("username");

		String FORM = "%n\t serverPort: %s %n\t activeProfiles: %s %n\t config: %s%n%n";
		System.out.printf(FORM, serverPort, activeProfiles, config);
		assertTrue(true);
	}

	@Test void restClient_test( ) {

		// https://docs.spring.io/spring-framework/reference/integration/rest-clients.html
		Map<String, String> map = Maps.newHashMap();
		map.put("address", "14.917313,-23.511313");
		map.put("email", "YOUR_EMAIL_HERE");

		RestClient restClient = RestClient.builder().build();
		RestClient.ResponseSpec responseSpec = restClient.get().uri(URLs[5]).retrieve();
		ResponseEntity<String> responseEntity = responseSpec.toEntity(String.class);
		String json = responseEntity.getBody();

		String txtLines = formatJson(json);
		System.out.println(txtLines);
		assertTrue(true);
	}

	// static
	public static String formatJson(String json) {

		String txtLines = "";
		try {
			DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter(DLM, SYS_LF);
			DefaultPrettyPrinter dfPrinter = new DefaultPrettyPrinter();
			dfPrinter.indentObjectsWith(indenter);
			dfPrinter.indentArraysWith(indenter);

			ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
			ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
			JsonNode jsonNode = objectMapper.readTree(json);
			txtLines = objectWriter.writeValueAsString(jsonNode);
			txtLines = objectMapper.writer(dfPrinter).writeValueAsString(jsonNode);
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return txtLines;
	}

	public static String formatObject(Object object) {

		String json = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(object); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return json;
	}

}

