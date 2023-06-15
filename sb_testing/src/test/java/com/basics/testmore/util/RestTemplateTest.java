package com.basics.testmore.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.testmore.util.RestTemplateTest.HOLIDAY_API.abstractapi;
import static com.basics.testmore.util.RestTemplateTest.HOLIDAY_API.calendarific;
import static com.basics.testmore.util.RestTemplateTest.HOLIDAY_API.holidays;
import static com.basics.testmore.util.UtilityMain.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.GET;

public class RestTemplateTest {

	public enum HOLIDAY_API {abstractapi, calendarific, holidays}

	private static final String URL_JSONTEST_IP = "http://ip.jsontest.com/";

	private static final Map<Object, String> MAP_URLS = Map.of(
		abstractapi, "https://holidays.abstractapi.com/v1",
		calendarific, "https://calendarific.com/api/v2/holidays",
		holidays, "https://holidayapi.com/v1/workdays"
	);

	private static final Map<Object, String> MAP_APIKEYS = Map.of(
		abstractapi, "4ad4dd73eda7408eabf33d1417bc3c20",
		calendarific, "a830f6a2905d2cb988eb7f02ae9d096755ce20a8",
		holidays, "ed0abac1-55d3-41da-9974-8819bcb67960"
	);

	private static final String YEAR = String.valueOf(LocalDate.now().getYear());

	@Test void RT_getForEntity( ) {

		StringBuilder stringBuilder = new StringBuilder();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate
			.getForEntity(URL_JSONTEST_IP, String.class);
		stringBuilder.append(responseEntity.getBody());

		System.out.print(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void RT_exchange_get( ) {

		StringBuilder stringBuilder = new StringBuilder();
		URI uri = null;
		try { uri = new URI(URL_JSONTEST_IP); }
		catch (URISyntaxException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<String> requestEntity = new RequestEntity(GET, uri);
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

		stringBuilder.append(responseEntity.getBody());
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void api_holiday_URI_abstractapi( ) {

		StringBuilder stringBuilder = new StringBuilder();

		DefaultUriBuilderFactory DUBF = new DefaultUriBuilderFactory(MAP_URLS.get(abstractapi));
		URI uri = DUBF.builder()
			.queryParam("api_key", MAP_APIKEYS.get(abstractapi))
			.queryParam("country", "US")
			.queryParam("year", YEAR)
			.queryParam("month", 12)
			.queryParam("day", 25)
			.build();

		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<String> requestEntity = new RequestEntity(GET, uri);
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

		stringBuilder.append(responseEntity.getBody());
		System.out.println(EOL + "uri: " + uri);
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void api_holiday_URI_calendarific_JSON( ) {

		DefaultUriBuilderFactory DUBF = new DefaultUriBuilderFactory(MAP_URLS.get(calendarific));
		URI uri = DUBF.builder()
			.queryParam("api_key", MAP_APIKEYS.get(calendarific))
			.queryParam("country", "US")
			.queryParam("year", YEAR)
			.queryParam("type", "national")
			.build();

		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<String> requestEntity = new RequestEntity(GET, uri);
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		System.out.println(EOL + "responseEntity: " + responseEntity.getBody());

		// get the data
		StringBuilder stringBuilder = new StringBuilder();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
			JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
			String json = objectWriter.writeValueAsString(jsonNode);

			// suppress logger
			String JSONPATH_LOGGER = "com.jayway.jsonpath.internal.path.CompiledPath";
			LoggerContext logContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			ch.qos.logback.classic.Logger log = logContext.getLogger(JSONPATH_LOGGER);
			log.setLevel(Level.INFO);

			// strip out info
			List<String> list = JsonPath.read(json, "$.response.holidays[*].name");
			AtomicInteger ai = new AtomicInteger();
			list.forEach(name -> {
					int aint = ai.getAndIncrement();
					String date = JsonPath.read(json, "$.response.holidays[" + aint + "].date.iso");
					stringBuilder.append(String.format("\t%02d %s %s\n", aint + 1, date, name));
				}
			);
		}
		catch (JsonProcessingException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(EOL + "uri: " + uri);
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void api_holiday_URI_holidays( ) {

		StringBuilder stringBuilder = new StringBuilder();

		DefaultUriBuilderFactory DUBF = new DefaultUriBuilderFactory(MAP_URLS.get(holidays));
		URI uri = DUBF.builder()
			.queryParam("key", MAP_APIKEYS.get(holidays))
			.queryParam("country", "US")
			.queryParam("start", "2022-01-01")
			.queryParam("end", "2022-12-31")
			.queryParam("public ", "true")
			.queryParam("format", "json")
			.queryParam("pretty", "true")
			.build();

		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<String> requestEntity = new RequestEntity(GET, uri);
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

		stringBuilder.append(responseEntity.getBody());
		System.out.println(EOL + "uri: " + uri);
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}
}
