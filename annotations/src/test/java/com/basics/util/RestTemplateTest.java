package com.basics.util;

import com.basics.samples.ClientHttpRequestInterceptor_Impl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static com.basics.util.UtilityMain.EOL;
import static com.basics.util.UtilityMain.exposeObject;
import static com.basics.util.UtilityMainTest.PATH_LOCAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.ACCEPT_CHARSET;
import static org.springframework.http.HttpHeaders.ACCEPT_ENCODING;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_ENCODING;
import static org.springframework.http.HttpHeaders.CONTENT_LANGUAGE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// @RunWith( MockitoJUnitRunner.class ) JUnit 4
public class RestTemplateTest {

	private static final Logger LOGGER = Logger.getLogger(RestTemplateTest.class.getName());
	private static final String HOST_EXT = "http://httpbin.org";
	private static final String FILENAME_BOOKS = "booksCatalog.json";
	private static final String FRMT = "\t%-20s %s\n";
	private static final String TESTSERVER_DOWNMSG = "I/O error on GET Connection refused; using Mock";
	public static final String DEFAULT_OAUTH =
		"{ \"access_token\": \"TOKEN_DEFAULT\", \"token_type\": \"TYPE_DEFAULT\", \"expires_in\": " +
			"\"EXPIRES_DEFAULT\", \"id_token\": \"ID_DEFAULT\" }";

	// get & post
	@Test void test_RT_objects( ) {
		//
		StringBuilder sb = new StringBuilder();
		RestTemplate restTemplate = new RestTemplate();
		URI uri = null;
		try {
			uri = new URI(HOST_EXT);
		}
		catch (URISyntaxException ex) {
			LOGGER.info(ex.getMessage());
		}
		HttpEntity<String> httpEntity = new HttpEntity<>("http_text");
		RequestEntity<String> requestEntity =
			RequestEntity.post(Objects.requireNonNull(uri)).body("request_text");
		ResponseEntity<String> responseEntity = new ResponseEntity<>("response_text", OK);
		Object[] objects = { restTemplate, httpEntity, requestEntity, responseEntity };

		Arrays.stream(objects).forEach(obj -> sb.append(exposeObject(obj)));

		System.out.print(sb);
		assertNotNull(sb);
	}

	@Test void test_RT_getForEntity( ) {
		//
		String txtLines = "";
		String url = HOST_EXT + "/get?sid=A123456";
		//
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		//
		ClientHttpRequestFactory CHRF = restTemplate.getRequestFactory();
		HttpStatus httpStatus = responseEntity.getStatusCode();
		String response = Objects.requireNonNull(responseEntity.getBody())
			.replaceAll("\\s+", " ").substring(0, 80);
		String headers = responseEntity.getHeaders().toString().replaceAll(",", ",\n\t\t");
		//
		txtLines += String.format(FRMT, "CHRF", CHRF);
		txtLines += String.format(FRMT, "getStatusCode", httpStatus);
		txtLines += String.format(FRMT, "getBody", response);
		txtLines += String.format(FRMT, "getHeaders", headers);
		//
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_RT_getForEntity_simplified( ) {
		//
		// ResponseEntity<String> responseEntity = restTemplate.getForEntity(txtURL, String.class);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = getForEntity_String(restTemplate, HOST_EXT);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		// RT.getForEntity( ) > RE.getStatusCode( ) > HS
		String txtLines = String.format("httpStatus: %s\n", httpStatus);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_postForEntity_logged( ) {
		//
		String txtLines = "";
		String url = HOST_EXT + "/post";
		String body = UtilityMain.getFileLocal(PATH_LOCAL + "books.json", "");
		//
		// add headers: MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> MVM = new LinkedMultiValueMap<>();
		MVM.add(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
		MVM.add("AnyId", "G002875");
		MVM.add("Phone", "614-377-7835");
		HttpEntity<String> httpEntity = new HttpEntity<>(body, MVM);
		//
		// add logging
		SimpleClientHttpRequestFactory SCHRF = new SimpleClientHttpRequestFactory();
		BufferingClientHttpRequestFactory BCHRF = new BufferingClientHttpRequestFactory(SCHRF);
		RestTemplate restTemplate = new RestTemplate(BCHRF);
		List<ClientHttpRequestInterceptor> list = new ArrayList<>();
		ClientHttpRequestInterceptor_Impl CHRII = new ClientHttpRequestInterceptor_Impl();
		list.add(CHRII);
		restTemplate.setInterceptors(list);
		//
		// send request
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, POST, httpEntity, String.class);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		String response = Objects.requireNonNull(responseEntity.getBody())
			.replaceAll("\\s+", " ").substring(0, 80);
		String headers = responseEntity.getHeaders().toString().replaceAll(",", ",\n\t\t");
		//
		// show
		txtLines += String.format(FRMT, "getStatusCode", httpStatus);
		txtLines += String.format(FRMT, "getBody", response);
		txtLines += String.format(FRMT, "getHeaders", headers);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_postForEntity_simplified( ) {
		//
		String body = "Here we go again!";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("AgentId", "G002875");
		httpHeaders.add("Phone", "614-377-7835");
		//
		HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity =
			exchange_Entity(restTemplate, HOST_EXT + "/post", POST, httpEntity);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		String txtLines = String.format("httpStatus: %s\n", httpStatus);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	// exchange
	@Test void test_RT_exchange_get( ) {
		//
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> httpEntity = new HttpEntity<>("");
		ResponseEntity<String> responseEntity =
			restTemplate.exchange(HOST_EXT, GET, httpEntity, String.class);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		String txtLines = String.format("httpStatus: %s\n", httpStatus);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_exchange_get_HttpHeaders( ) {
		//
		StringBuilder sb = new StringBuilder();
		String body = "{summary:{forename:Martin, surname:George, work: programmer, affiliation:Christian}}";
		String auth_base64Creds = Base64.getEncoder().encodeToString("username:password".getBytes());

		// request
		HttpEntity<String> httpEntity = getHttpEntity_String(auth_base64Creds, body);
		HttpHeaders httpHeader_REQ = httpEntity.getHeaders();
		sb.append(EOL + "httpHeader_REQ:\n");
		httpHeader_REQ.keySet().forEach(key -> sb.append(String.format(FRMT, key, httpHeader_REQ.get(key))));
		String body_REQ =
			Objects.requireNonNull(httpEntity.getBody()).replaceAll("\\s+", " ").substring(0, 80);
		sb.append(EOL + "body_REQ:\n\t").append(body_REQ).append(EOL);
		//
		// exchange
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity =
			restTemplate.exchange(HOST_EXT, GET, httpEntity, String.class);
		//
		// response
		HttpStatus httpStatus = responseEntity.getStatusCode();
		HttpHeaders httpHeader_RSP = responseEntity.getHeaders();
		sb.append(EOL + "httpStatus:\n\t").append(httpStatus).append(EOL);
		sb.append(EOL + "httpHeader_RSP:\n");
		httpHeader_RSP.keySet()
			.forEach(key -> sb.append(String.format(FRMT, key, httpHeader_RSP.get(key))));
		String body_RSP = Objects.requireNonNull(responseEntity.getBody())
			.replaceAll("\\s+", " ").substring(0, 80);
		sb.append(EOL + "body_RSP:\n\t").append(body_RSP);
		//
		System.out.println(sb);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_exchange_get_simplified( ) {
		//
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> httpEntity = new HttpEntity<>("");
		ResponseEntity<String> responseEntity = exchange_Entity(restTemplate, HOST_EXT, GET, httpEntity);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		String txtLines = String.format("httpStatus: %s\n", httpStatus);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_exchange_post_body( ) {
		//
		String txtLines = "";
		String url = HOST_EXT + "/post";
		String body = UtilityMain.getFileLocal(PATH_LOCAL + "books.json", "");
		//
		// add headers: MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		MultiValueMap<String, String> MVM = new LinkedMultiValueMap<>();
		MVM.add(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
		MVM.add("AnyId", "G002875");
		MVM.add("Phone", "614-377-7835");
		HttpEntity<String> httpEntity = new HttpEntity<>(body, MVM);
		//
		// send request
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, POST, httpEntity, String.class);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		String response = Objects.requireNonNull(responseEntity.getBody())
			.replaceAll("\\s+", " ").substring(0, 80);
		String headers = responseEntity.getHeaders().toString().replaceAll(",", ",\n\t\t");
		//
		// show
		txtLines += String.format(FRMT, "getStatusCode", httpStatus);
		txtLines += String.format(FRMT, "getBody", response);
		txtLines += String.format(FRMT, "getHeaders", headers);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_exchange_post_simplified( ) {
		//
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> httpEntity = new HttpEntity<>("bar");
		ResponseEntity<String> responseEntity =
			exchange_Entity(restTemplate, HOST_EXT + "/post", POST, httpEntity);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		String txtLines = String.format("httpStatus: %s\n", httpStatus);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test void test_RT_SCHRF( ) {
		//
		String txtLines = "";
		int timeout = 5000;
		//
		SimpleClientHttpRequestFactory SCHRF = new SimpleClientHttpRequestFactory();
		SCHRF.setConnectTimeout(timeout);
		SCHRF.setReadTimeout(timeout);
		//
		// RestTemplate restTemplate = new RestTemplate(SCHRF);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(SCHRF);
		ResponseEntity<String> responseEntity = getForEntity_String(restTemplate, HOST_EXT);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		txtLines += String.format("SCHRF: %s\n", SCHRF);
		txtLines += String.format("responseEntity.getBody(): %s\n", responseEntity.getBody());
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	@Test @Disabled( "general reasons" ) void test_RT_HCCHRF( ) {
		//
		// needed org.apache.httpComponents:httpclient:4.3.4
		String txtLines = "";
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory HCCHRF = new HttpComponentsClientHttpRequestFactory();
		HCCHRF.setConnectTimeout(timeout);
		//
		RestTemplate restTemplate = new RestTemplate(HCCHRF);
		ResponseEntity<String> responseEntity = getForEntity_String(restTemplate, HOST_EXT);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		String bodyRSP = Objects.requireNonNull(responseEntity.getBody())
			.replaceAll("\\s+", "").substring(0, 80);
		//
		txtLines += String.format("HCCHRF: %s\n", HCCHRF);
		txtLines += String.format("responseEntity.getBody(): %s\n", bodyRSP);
		System.out.println(txtLines);
		assertEquals(OK, httpStatus);
	}

	// helpers
	public static HttpEntity<String> getHttpEntity_String(String auth_base64Creds, String body) {

		HttpHeaders httpHeaders_REQ = new HttpHeaders();
		httpHeaders_REQ.add(AUTHORIZATION, "Basic " + auth_base64Creds);
		httpHeaders_REQ.add(ACCEPT_CHARSET, "utf-8, iso-8859-1");
		httpHeaders_REQ.add(ACCEPT_ENCODING, "gzip");
		httpHeaders_REQ.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		httpHeaders_REQ.add(CONTENT_ENCODING, "gzip");
		httpHeaders_REQ.add(CONTENT_LANGUAGE, "en-US");
		httpHeaders_REQ.add(USER_AGENT, "PostmanRuntime/7.26.5");
		HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders_REQ);

		return httpEntity;
	}

	public static ResponseEntity<String> getForEntity_String(RestTemplate restTemplate, String txtUrl) {
		//
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = restTemplate.getForEntity(txtUrl, String.class);
		}
		catch (ResourceAccessException ex) {
			LOGGER.info(TESTSERVER_DOWNMSG);
			String body = UtilityMain.getFileLocal(PATH_LOCAL + FILENAME_BOOKS, "");
			//
			MultiValueMap<String, String> MVM = new LinkedMultiValueMap<>();
			MVM.add(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
			MVM.add("AgentId", "G002875");
			MVM.add("Phone", "614-377-7835");
			responseEntity = new ResponseEntity<>(body, MVM, OK);
		}
		return responseEntity;
	}

	public static ResponseEntity<String> exchange_Entity(RestTemplate RT, String txtUrl,
		HttpMethod httpMethod, HttpEntity<?> httpEntity) {
		//
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = RT.exchange(txtUrl, httpMethod, httpEntity, String.class);
		}
		catch (ResourceAccessException ex) {
			LOGGER.info(TESTSERVER_DOWNMSG);
			String body = UtilityMain.getFileLocal(PATH_LOCAL + FILENAME_BOOKS, "");
			MultiValueMap<String, String> MVM = new LinkedMultiValueMap<>();
			MVM.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
			responseEntity = new ResponseEntity<>(body, MVM, OK);
		}
		return responseEntity;
	}

	public static MultiValueMap<String, String> getMvmSample( ) {
		//
		MultiValueMap<String, String> MVM = new LinkedMultiValueMap<>();
		MVM.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
		return MVM;
	}
}
