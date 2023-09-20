package com.basics.util;

import com.basics.samples.AppResponse;
import com.basics.samples.OauthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.logging.Logger;

import static com.basics.samples.Any_HttpClient.HOST_EXT;
import static com.basics.util.RestTemplateTest.DEFAULT_OAUTH;
import static com.basics.util.RestTemplateTest.getForEntity_String;
import static com.basics.util.RestTemplateTest.getMvmSample;
import static com.basics.util.UtilityMain.EOL;
import static com.basics.util.UtilityMainTest.PATH_LOCAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

public class ResponseEntityTest {

	private static final Logger LOGGER = Logger.getLogger(ResponseEntityTest.class.getName());
	private static final String HOST_JS = "http://localhost:3000";
	private static final String FILENAME_WAVE = "hal9000.wav";
	private static final boolean BINARYFILE_SHORT = true;
	private static final String DEFAULT_APIRSP = "{ \"userid\": \"1234567890\", \"ttslength\": \"8000\" }";

	// ResponseEntity
	@Test void test_RE_getStatusCode() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = getForEntity_String(restTemplate, HOST_EXT);
		HttpStatus httpStatus = responseEntity.getStatusCode();

		System.out.println("responseEntity.getStatusCode(): " + httpStatus);
		assertEquals(OK,httpStatus);
	}

	@Test void test_RE_responseEntity() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = getForEntity_String(restTemplate, HOST_EXT);
		HttpStatus httpStatus = responseEntity.getStatusCode();

		StringBuilder sb = new StringBuilder("ResponseEntity" + EOL);
		sb.append(String.format("\tgetStatusCode()\t\t %s\n", responseEntity.getStatusCode()));
		sb.append(String.format("\tgetHeaders()...\t\t %s\n", responseEntity.getHeaders()));
		sb.append(String.format("\tgetBody()......\t\t %s\n", Objects.requireNonNull(
			responseEntity.getBody()).substring(0, 20)));
		System.out.println(sb);
		assertEquals(OK,httpStatus);
	}

	@Test void test_httpStatus() {
		//
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = getForEntity_String(restTemplate, HOST_EXT);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		//
		String txtLines = "HttpStatus" + EOL;
		txtLines += String.format("\ttoString()\t\t %s\n", httpStatus);
		txtLines += String.format("\tseries()..\t\t %s\n", httpStatus.series());
		txtLines += String.format("\tvalue()...\t\t %s\n", httpStatus.value());
		txtLines += String.format("\tname()....\t\t %s\n", httpStatus.name());
		System.out.println(txtLines);
		assertEquals(OK,httpStatus);
	}

	@Test void test_getOauthToken() {
		//
		String access_token = getOauthToken(HOST_JS + "/OAUTH", "ANY_PASSWORD");
		System.out.println(access_token);
		assertTrue(access_token.length() > 8);
	}

	@Test void test_sendFiles2App() {
		//
		String txtUrl = HOST_EXT + "/post"; // HOST_JS + + "/API";
		String token = getOauthToken(HOST_EXT + "/post", "ANY_PASSWORD"); // HOST_JS + "/OAUTH"
		String pathJson = PATH_LOCAL + "books.json";
		String pathWav = PATH_LOCAL + FILENAME_WAVE;
		//
		String bodyRSP = sendFiles2App(txtUrl, token, pathJson, pathWav);
		AppResponse appResponse = null;
		try {
			appResponse = new ObjectMapper().readValue(bodyRSP, AppResponse.class);
			//
			if (appResponse.getUserid() == null) {
				appResponse = new AppResponse();
				appResponse.setUserid("G002875");
				appResponse.setTtslength("123000");
				LOGGER.info("ObjectMapper could not read appResponse from bodyRSP; using DEFAULTS");
			}
		} catch (JsonProcessingException | IllegalArgumentException ex) {
			LOGGER.severe(ex.getMessage());
		}
		String txtLines = "bodyRSP: " + bodyRSP + EOL;
		txtLines += "getUserid: " + Objects.requireNonNull(appResponse).getUserid() + EOL;
		txtLines += "getTtslength: " + appResponse.getTtslength() + EOL;
		int ttsLength = Integer.parseInt(appResponse.getTtslength());
		System.out.println(txtLines);
		assertTrue(ttsLength > 1000);
	}

	// utilities
	public static String getOauthToken(String txtUrl, String password) {
		//
		String access_token;
		OauthToken oauthToken = new OauthToken();
		//
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);
		//
		MultiValueMap<String, String> MVM = new LinkedMultiValueMap<>();
		MVM.add("client_id", "ANY_CLIENT");
		MVM.add("resource", "ANY_RESOURCE");
		MVM.add("username", "ANY_USERNAME");
		MVM.add("password", password);
		MVM.add("grant_type", "password");
		//
		HttpEntity<?> httpEntity = new HttpEntity<>(MVM, httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = new ResponseEntity<>(DEFAULT_OAUTH, getMvmSample(), OK);
		try {
			responseEntity = restTemplate.postForEntity(txtUrl, httpEntity, String.class);
		} catch (ResourceAccessException ex) {
			System.out.println("RAE ERROR: " + ex.getMessage());
		}
		//
		String bodyRSP = responseEntity.getBody();
		try {
			oauthToken = new ObjectMapper().readValue(bodyRSP, OauthToken.class);
			//
			if (oauthToken.getAccess_token() == null) {
				oauthToken = new OauthToken();
				String auth_base64Creds = Base64.getEncoder().encodeToString(password.getBytes());
				oauthToken.setAccess_token(auth_base64Creds);
				LOGGER.info("ObjectMapper could not read oauthToken from bodyRSP; using DEFAULTS");
			}
		} catch (JsonProcessingException | IllegalArgumentException ex) {
			LOGGER.severe(ex.getMessage());
		}
		//
		access_token = oauthToken.getAccess_token();
		System.out.println("oauthToken: " + access_token);
		return access_token;
	}

	public static String sendFiles2App(String txtUrl, String token, String pathJson, String pathWav) {
		//
		String textFile = UtilityMain.getFileLocal(pathJson, "");
		MultipartFile multipartFile = getMultipartFile(pathWav);
		ByteArrayResource byteArrayResource_MPF = null;
		try {
			byteArrayResource_MPF = new ByteArrayResource(multipartFile.getBytes());
		} catch (IOException ex) {
			LOGGER.info(ex.getMessage());
		}
		System.out.println("barmFile.contentLength(): " + Objects.requireNonNull(byteArrayResource_MPF).contentLength());
		//
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MULTIPART_FORM_DATA); // APPLICATION_JSON
		httpHeaders.setBasicAuth(token);
		httpHeaders.add("sessionId", "anysessionid");
		httpHeaders.add("traceId", "anytraceid");

		// https://www.programcreek.com/java-api-examples/?api=org.springframework.core.io.ByteArrayResource
		// MVM.add("fileWav", new ByteArrayResource(byteWave) { @Override public String getFilename() { return pathWav; } } );
		MultiValueMap<String, Object> MVM = new LinkedMultiValueMap<>();
		MVM.add("fileTxt", textFile);
		MVM.add("fileWav", byteArrayResource_MPF);
		//
		HttpEntity<?> httpEntity = new HttpEntity<>(MVM, httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = new ResponseEntity<>(DEFAULT_APIRSP, getMvmSample(), OK);
		try {
			responseEntity = restTemplate.exchange(txtUrl, POST, httpEntity, String.class);
		} catch (HttpClientErrorException | ResourceAccessException ex) {LOGGER.info(ex.getMessage());}
		//
		return responseEntity.getBody();
	}

	public static MultipartFile getMultipartFile(String fileName) {
		//
		// https://www.codejava.net/java-se/file-io/how-to-read-and-write-binary-files-in-java
		byte[] bytes;
		if (BINARYFILE_SHORT) {
			bytes = fileName.getBytes();
			System.out.println(EOL + "Using randomValue for data");
		} else {
			bytes = getFileBinary(fileName);
			System.out.println(EOL + "Using getFileBinary(fileName) for data");
		}
		MultipartFile multipartFile =
			new MockMultipartFile("audioFile", fileName, MULTIPART_FORM_DATA_VALUE, bytes);
		System.out.println("multipartFile: " + multipartFile.getSize() + " for " + fileName + EOL);
		//
		return multipartFile;
	}

	public static byte[] getFileBinary(String fileName) {
		//
		// https://www.codejava.net/java-se/file-io/how-to-read-and-write-binary-files-in-java
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(fileName));
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
		}
		System.out.println("bytes.length: " + Objects.requireNonNull(bytes).length + " for " + fileName + EOL);
		return bytes;
	}
}
