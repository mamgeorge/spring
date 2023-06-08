package com.basics.testmore.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.basics.testmore.util.UtilityMain.EOL;
import static com.basics.testmore.util.UtilityMain.getFileLocal;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.GET;

/*
	https://jsonbible.com/
	https://www.jsontest.com/
	https://apipheny.io/free-api/
	https://rapidapi.com/blog/most-popular-api/ | Google Search, NLP Translation
*/
public class RestSamples {

	public static final String PATHFILE_LOCAL = "src/test/resources/";
	public static final String URL_JSONBIBLE = "https://jsonbible.com/search/verses.php";
	public static final Map<String, String> MAP_JSONTEST = new HashMap<>();

	static {
		MAP_JSONTEST.put("ip", "http://ip.jsontest.com/");
		MAP_JSONTEST.put("ipMime", "http://ip.jsontest.com/?mime=5");
		MAP_JSONTEST.put("ipACAO",
			"http://ip.jsontest.com/?alloworigin=false"); // Access-Control-Allow-Origin
		MAP_JSONTEST.put("headers", "http://headers.jsontest.com/");
		MAP_JSONTEST.put("date", "http://date.jsontest.com");
		MAP_JSONTEST.put("time", "http://time.jsontest.com");
		MAP_JSONTEST.put("echo", "http://echo.jsontest.com/key/value/one/two");
		MAP_JSONTEST.put("code", "http://code.jsontest.com");
		MAP_JSONTEST.put("cookie", "http://cookie.jsontest.com/");
		MAP_JSONTEST.put("md5", "http://md5.jsontest.com/?text=GEORGE");
		// MAP_JSONTEST.put("validate","http://validate.jsontest.com/?json={'key':'value'}");
	}

	@Test void jsonRead( ) {

		String txtLines = "";
		String json = getFileLocal(PATHFILE_LOCAL + "google_response.json", "");

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			txtLines = jsonArray.getJSONObject(0).getString("title");
		}
		catch (JSONException ex) {System.out.println("ERROR: " + ex.getMessage());}
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void HttpClient( ) {

		String XRAPIDAPIHOST = "google-search72.p.rapidapi.com";
		String XRAPIDAPIKEY = "70e87efb55mshf9f42022d0c3878p1a6ce0jsn88b17922572f";

		String url = "https://" + XRAPIDAPIHOST + "/search" + "?query="
			+ "FUXI" // must be encoded
			//	+"&gl=us"+"&lr=en"+"&num=10"+"&start=0"+"&sort=relevance"
			;
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("X-RapidAPI-Key", XRAPIDAPIKEY)
			.header("X-RapidAPI-Host", XRAPIDAPIHOST)
			.method(GET.name(), HttpRequest.BodyPublishers.noBody())
			.build();

		HttpResponse<String> httpResponse = null;
		try {
			httpResponse = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
		}
		catch (IOException | InterruptedException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(httpResponse.body());
		assertNotNull(httpResponse);
	}

	@Test void exchange_Verse( ) {

		String txtLines = "";

		String urq = "?json={x}";
		String urx = "{'book':'John','chapter':'3','verse':'16','version':'nasb'}".replaceAll("'", "\\\"");
		String url = URL_JSONBIBLE + urq;

		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(url, String.class, urx);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			txtLines = jsonObject.getString("text");
		}
		catch (JSONException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void exchange_Headers( ) {

		String url = MAP_JSONTEST.get("headers");
		MultiValueMap<String, String> MVP = new LinkedMultiValueMap<>();
		MVP.add("alpha", "beta");
		MVP.add("aleph", "beth");

		RequestEntity<String> requestEntity = new RequestEntity<>(MVP, GET, URI.create(url));
		ResponseEntity<String> responseEntity;
		RestTemplate restTemplate = new RestTemplate();
		responseEntity = restTemplate.exchange(requestEntity, String.class);

		String body = responseEntity.getBody();
		System.out.println(body);
		assertNotNull(body);
	}

	@Test void exchange_RequestEntity( ) {

		MultiValueMap<String, String> MVP = new LinkedMultiValueMap<>();
		MVP.add("profile", "Developer");
		MVP.add("tech", "Java");

		RequestEntity<String> requestEntity;
		ResponseEntity<String> responseEntity;
		RestTemplate restTemplate = new RestTemplate();

		StringBuilder stringBuilder = new StringBuilder();
		Set<String> set = MAP_JSONTEST.keySet();
		URI uri;
		for ( String url : set ) {
			uri = URI.create(MAP_JSONTEST.get(url));
			requestEntity = new RequestEntity<>(MVP, GET, uri);
			responseEntity = restTemplate.exchange(requestEntity, String.class);
			stringBuilder.append(responseEntity.getBody() + EOL);
		}
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void exchange_HttpEntity( ) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity;
		ResponseEntity<String> responseEntity;
		RestTemplate restTemplate = new RestTemplate();

		StringBuilder stringBuilder = new StringBuilder();
		Set<String> set = MAP_JSONTEST.keySet();
		String uri;
		for ( String url : set ) {
			uri = MAP_JSONTEST.get(url);
			httpEntity = new HttpEntity<>(headers);
			responseEntity = restTemplate.exchange(uri, GET, httpEntity, String.class);
			stringBuilder.append(responseEntity.getBody() + EOL);
		}
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}
}