package com.basics.dbsqlite;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.TEXT_PLAIN_VALUE;

/*
	https://www.baeldung.com/introduction-to-wiremock
	https://wiremock.org/docs/quickstart/java-junit/
*/
class WireMockTest {

	public static String URL_HOST = "localhost";
	public static String URL_PATH = "/mamgeorge";
	public static int URL_PORT = 8080;
	public static String URL_URI = "http://" + URL_HOST + ":" + URL_PORT + URL_PATH;

	private static final String RESPONSE_BODY_GET = "<response>SUCCESS</response>";
	private static final String REQUEST_BODY_POST = "{'name':'mamgeorge', 'age':40}".replaceAll("'", "\"");
	private static final String RESPONSE_BODY_POST =
		"{'message':'Data received successfully!'}".replaceAll("'", "\"");

	private final WireMockServer wireMockServer = new WireMockServer();

	// java.net.http
	@Test void test_wiremock_HttpClient_GET( ) {

		wireMockServer.start();
		configure_WireMockServer_GET();

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(URL_URI))
			.header(CONTENT_TYPE, TEXT_PLAIN_VALUE)
			.GET().build();

		HttpClient httpClient = HttpClient.newBuilder().build();

		HttpResponse<String> httpResponse = null;
		try { httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); }
		catch (IOException | InterruptedException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		String responseString = Objects.requireNonNull(httpResponse).body();

		wireMockServer.stop();

		System.out.println("responseString: " + responseString);
		assertEquals(RESPONSE_BODY_GET, responseString);
	}

	@Test void test_wiremock_HttpClient_POST( ) {

		wireMockServer.start();
		configure_WireMockServer_POST();

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(URL_URI))
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.POST(HttpRequest.BodyPublishers.ofString(REQUEST_BODY_POST, UTF_8))
			.build();

		HttpClient httpClient = HttpClient.newBuilder().build();

		HttpResponse<String> httpResponse = null;
		try { httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); }
		catch (IOException | InterruptedException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		String responseString = httpResponse.body();

		wireMockServer.stop();

		System.out.println("responseString: " + responseString);
		assertEquals(RESPONSE_BODY_POST, responseString);
	}

	// org.apache.hc.client5.http.classic.methods
	@Test void test_wiremock_CloseableHC_GET( ) {

		wireMockServer.start();
		configure_WireMockServer_GET();

		String responseString = "";
		try {
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(URL_URI);
			httpGet.addHeader(CONTENT_TYPE, containing(TEXT_PLAIN_VALUE));
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			responseString = convertChr2Str(closeableHttpResponse);
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		wireMockServer.stop();

		System.out.println("responseString: " + responseString);
		assertEquals(RESPONSE_BODY_GET, responseString);
	}

	@Test void test_wiremock_CloseableHC_POST( ) {

		wireMockServer.start();
		configure_WireMockServer_POST();

		String responseString = "";
		try {
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(URL_URI);
			httpPost.addHeader(CONTENT_TYPE, containing(APPLICATION_JSON_VALUE));
			StringEntity stringEntity = new StringEntity(REQUEST_BODY_POST, APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
			responseString = convertChr2Str(closeableHttpResponse);
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		wireMockServer.stop();

		System.out.println("responseString: " + responseString);
		assertEquals(RESPONSE_BODY_POST, responseString);
	}

	// statics: com.github.tomakehurst.wiremock
	private static void configure_WireMockServer_GET( ) {

		configureFor(URL_HOST, URL_PORT);

		stubFor(get(URL_PATH)
			.withHeader(CONTENT_TYPE, containing(TEXT_PLAIN_VALUE))
			.willReturn(ok()
				.withHeader(CONTENT_TYPE, TEXT_PLAIN_VALUE)
				.withBody(RESPONSE_BODY_GET)));
	}

	private static void configure_WireMockServer_POST( ) {

		configureFor(URL_HOST, URL_PORT);

		stubFor(post(urlEqualTo(URL_PATH))
			.withRequestBody(equalToJson(REQUEST_BODY_POST, true, true))
			.willReturn(aResponse()
				.withStatus(SC_OK)
				.withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
				.withBody(RESPONSE_BODY_POST)));
	}


	// statics
	private static String convertChr2Str(CloseableHttpResponse closeableHttpResponse) {

		String responseString = "";
		try {
			InputStream responseStream = closeableHttpResponse.getEntity().getContent();
			Scanner scanner = new Scanner(responseStream, UTF_8);
			responseString = scanner.useDelimiter("\\Z").next();
			scanner.close();
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return responseString;
	}
}
