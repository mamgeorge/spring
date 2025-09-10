package com.basics.testmore.util;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WireMockTest( httpsEnabled = true, httpsPort = 9090, proxyMode = true )
public class MockTestsWire {

	private static final String BEARER_TOKEN = "Bearer 77d9b8f0-fafe-3778-addf-2755bdc53c88";
	private static final String JSON_CONTENT = "{\"hello\":\"world\"}";
	private static final String ENDPOINT_HOST = "mydomain.com";
	private static final String ENDPOINT_PATH = "/sample";
	private static final String ENDPOINT = "https://" + ENDPOINT_HOST + ":" + "9090" + ENDPOINT_PATH;

	@Test @Disabled
	public void closeableHttpResponse( ) {

		String body = null;
		Map<String, String> headers = new HashMap<>();
		headers.put(AUTHORIZATION, BEARER_TOKEN);

		stubFor(get(ENDPOINT_PATH)
			.withHeader(AUTHORIZATION, equalTo(BEARER_TOKEN))
			.withHost(equalTo(ENDPOINT_HOST))
			.willReturn(aResponse().withBody(JSON_CONTENT).withStatus(200)));

		HttpGet httpGet = new HttpGet(ENDPOINT); // apache
		httpGet.setHeaders(
			headers.entrySet().stream().map(entry -> new BasicHeader(entry.getKey(), entry.getValue()))
				.toArray(Header[]::new));

		try {
			CloseableHttpClient closeableHttpClient = createAcceptSelfSignedCertificateClient();
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			body = EntityUtils.toString(closeableHttpResponse.getEntity(), Charset.defaultCharset());
		}
		catch (IOException | ParseException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		assertEquals(JSON_CONTENT, body);
	}

	private static CloseableHttpClient createAcceptSelfSignedCertificateClient( ) {

		CloseableHttpClient closeableHttpClient = null;
		SSLContext sslContext = null;
		try {
			sslContext = SSLContextBuilder
				.create()
				.loadTrustMaterial(new TrustSelfSignedStrategy())
				.build();
		}
		catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		HostnameVerifier hostnameVerifier = new NoopHostnameVerifier(); // all
		SSLConnectionSocketFactory sslCsFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		closeableHttpClient = HttpClients
			.custom()
		//	.setSSLSocketFactory(sslCsFactory)
			.build();
		return closeableHttpClient;
	}
}
