package com.basics.testmore.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.testmore.util.RestSamples.HOLIDAY_API.abstractapi;
import static com.basics.testmore.util.RestSamples.HOLIDAY_API.calendarific;
import static com.basics.testmore.util.RestSamples.HOLIDAY_API.holidays;
import static com.basics.testmore.util.UtilityMain.EOL;
import static com.basics.testmore.util.UtilityMain.getFileLocal;
import static com.basics.testmore.util.UtilityMainTests.PATHFILE_LOCAL;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/*
	https://jsonbible.com/
	https://www.jsontest.com/
	https://apipheny.io/free-api/
	https://rapidapi.com/blog/most-popular-api/ | Google Search, NLP Translation
*/
public class RestSamples {

	public enum HOLIDAY_API {abstractapi, calendarific, holidays}

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

	public static final String URL_JSONBIBLE = "https://jsonbible.com/search/verses.php";
	private static final String URL_JSONTEST_IP = "http://ip.jsontest.com/";
	private static final String URL_JSONPLACE = "https://jsonplaceholder.typicode.com/";  // users, comments, posts, photos
	private static final String YEAR = String.valueOf(LocalDate.now().getYear());

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

	// templates
	@Test void RT_getForEntity( ) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL_JSONTEST_IP, String.class);

		String body = responseEntity.getBody();
		System.out.println("body" + EOL + body);
		assertNotNull(responseEntity);
	}

	@Test void RT_exchange_get( ) {

		URI uri = null;
		try { uri = new URI(URL_JSONTEST_IP); }
		catch (URISyntaxException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		RequestEntity<String> requestEntity = new RequestEntity(GET, uri);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

		String body = responseEntity.getBody();
		System.out.println("body" + EOL + body);
		assertNotNull(responseEntity);
	}

	@Test void RT_exchange_getJson( ) {

		String URL = URL_JSONPLACE + "users";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(ACCEPT, APPLICATION_JSON_VALUE);
		httpHeaders.set(AUTHORIZATION, getAuthorization());
		HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL, GET, httpEntity, String.class);

		String body = responseEntity.getBody();
		System.out.println("responseEntity" + EOL + responseEntity);
		System.out.println("body" + EOL + body);
		assertNotNull(responseEntity);
	}

	@Test void RT_exchange_post( ) {

		String[] urls = {
			"http://validate.jsontest.com",
			"http://validate.jsontest.com/?json={'key':'value'}".replaceAll("'","\"")};
		String filename = "contents.txt";
		String fileclob = getFileLocal(PATHFILE_LOCAL + filename, "");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE);
		httpHeaders.set("json", "{'key':'value'}".replaceAll("'","\""));

		MultiValueMap<String,Object> MVM_FORMDATA = new LinkedMultiValueMap<>();
		ByteArrayResource byteArrayResource = new ByteArrayResource(fileclob.getBytes(UTF_8), filename) {
			@Override public String getFilename() {return filename;}
		};
		MVM_FORMDATA.add("file", byteArrayResource);

		// HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity(MVM_FORMDATA, httpHeaders);
		HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(urls[0], POST, httpEntity, String.class);

		String body = responseEntity.getBody();
		System.out.println("responseEntity" + EOL + responseEntity);
		System.out.println("body" + EOL + body);
		assertNotNull(responseEntity);
	}

	// apis
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

	// utilities
	private String getAuthorization() {

		String user = System.getenv("USERNAME");
		String pass = System.getenv("PASSWORD");

		String userpass = user + ":" + pass;
		String userpassEncoded = Base64.getEncoder().encodeToString(userpass.getBytes());
		String authEncoded = "Basic " + userpassEncoded;
		return authEncoded;
	}
}