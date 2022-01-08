package com.basics.samples;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

public class Any_HttpClient {
	//
	public static final String HOST_EXT = "https://httpbin.org/";
	public static final String FRMT = "\t%-15s %s\n";

	private static final HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();

	public static void main(String[] args) {
		//
		String txtLines = "";
		HttpRequest httpRequest;
		httpRequest = requestGet();
		txtLines += handleResponse(httpRequest);
		//
		httpRequest = requestPost();
		txtLines += handleResponse(httpRequest);
		//
		System.out.println(txtLines);
		System.out.println("DONE");
	}

	private static HttpRequest requestGet() {
		//
		System.out.println("Testing " + GET);
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(HOST_EXT + GET.toString().toLowerCase()))
				.setHeader(USER_AGENT, "Java11Client Bot")
				.build();
		return httpRequest;
	}

	private static HttpRequest requestPost() {
		//
		System.out.println("Testing " + POST);
		Map<Object, Object> mapData = new HashMap<>();
		mapData.put("username", "G002875");
		mapData.put("password", "1964343");
		mapData.put("custom", "secret");
		mapData.put("timestamp", Instant.now());
		String formData = buildFormDataFromMap(mapData);
		System.out.println("formData: " + formData);
		//
		HttpRequest.BodyPublisher HRBP = HttpRequest.BodyPublishers.ofString(formData);
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.POST(HRBP)
				.uri(URI.create(HOST_EXT + POST.toString().toLowerCase()))
				.setHeader(USER_AGENT, "Java11Client Bot")
				.header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
				.build();
		//
		return httpRequest;
	}

	private static String handleResponse(HttpRequest httpRequest) {
		//
		String txtLines = "";
		HttpResponse<String> httpResponse = null;
		HttpResponse.BodyHandler<String> bodyHandlers = HttpResponse.BodyHandlers.ofString();
		try {
			httpResponse = httpClient.send(httpRequest, bodyHandlers);
		} catch (IOException | InterruptedException ex) {System.out.println("ERROR: " + ex.getMessage());}
		//
		txtLines += String.format(FRMT, "statusCode", httpResponse.statusCode());
		txtLines += String.format(FRMT, "body", httpResponse.body());
		assert (httpResponse.statusCode() == OK.value());
		return txtLines;
	}

	private static String buildFormDataFromMap(Map<Object, Object> mapData) {
		//
		StringBuilder strb = new StringBuilder();
		for (Map.Entry<Object, Object> mapEntry : mapData.entrySet()) {
			//
			if (strb.length() > 0) {
				strb.append("&");
			}
			strb.append(URLEncoder.encode(mapEntry.getKey().toString(), UTF_8));
			strb.append("=");
			strb.append(URLEncoder.encode(mapEntry.getValue().toString(), UTF_8));
		}
		return strb.toString();
	}
}
