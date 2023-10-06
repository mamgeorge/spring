package com.basics.securing.utils;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Objects;

import static com.basics.securing.utils.SSLExchange.CLIENT_KEYSTORE;
import static com.basics.securing.utils.SSLExchange.CLIENT_TRUSTSTORE;
import static com.basics.securing.utils.SSLExchange.PASSWORD_KEYS;
import static com.basics.securing.utils.SSLExchange.PASSWORD_TRST;
import static com.basics.securing.utils.SSLExchange.SERVER_KEYSTORE;
import static com.basics.securing.utils.SSLExchange.SERVER_TRUSTSTORE;
import static com.basics.securing.utils.SSLExchange.getExchangeOneWay;
import static com.basics.securing.utils.SSLExchange.getHttpClient;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.GET;

class SSLExchangeTest {

	private static final String[] URLS =
		{ "http://httpbin.org", "http://localhost:80", "localhost", "127.0.0.1" };

	// SSL OneWay
	@Test @Disabled( "INCOMPLETE" ) void test_OneWay( ) {

		String url = URLS[0];
		char[] charsPassword = PASSWORD_TRST.toCharArray();
		URL urlTrustStore = null;
		try { urlTrustStore = new URL("file:///" + SERVER_TRUSTSTORE); }
		catch (MalformedURLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		RestTemplate restTemplate = null;
		try {
			SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(urlTrustStore, charsPassword)
				.build();

			HttpClient httpClient = getHttpClient(sslContext);
			ClientHttpRequestFactory CHRF = new HttpComponentsClientHttpRequestFactory(httpClient);

			restTemplate = new RestTemplate(CHRF);
		}
		catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException |
		       KeyManagementException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		ResponseEntity<String> responseEntity = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>(null, httpHeaders);
		responseEntity = Objects.requireNonNull(restTemplate).exchange(url, GET, httpEntity, String.class);

		String txtlines = responseEntity.getBody();
		System.out.println(txtlines);
		assertNotNull(txtlines);
	}

	// SSL OneWay
	@Test @Disabled( "INCOMPLETE" ) void test_getExchangeOneWay( ) {

		String txtlines = getExchangeOneWay(SERVER_TRUSTSTORE, PASSWORD_TRST, URLS[0]);
		System.out.println(txtlines);
		assertNotNull(txtlines);
	}

	// SSL TwoWay
	@Test @Disabled( "INCOMPLETE" ) void test_getExchangeTwoWay( ) {

		if(false) {
			System.setProperty("javax.net.ssl.keyStore", SERVER_KEYSTORE);
			System.setProperty("javax.net.ssl.keyStorePassword", PASSWORD_KEYS);
			System.setProperty("javax.net.ssl.trustStore", SERVER_TRUSTSTORE);
			System.setProperty("javax.net.ssl.trustStorePassword", PASSWORD_TRST);

			System.setProperty("javax.net.ssl.keyStore", CLIENT_KEYSTORE);
			System.setProperty("javax.net.ssl.keyStorePassword", PASSWORD_KEYS);
			System.setProperty("javax.net.ssl.trustStore", CLIENT_TRUSTSTORE);
			System.setProperty("javax.net.ssl.trustStorePassword", PASSWORD_TRST);
		}

		String[] args = { };

		Thread threadSrv = new Thread(( ) -> SSLSocketServer.main(args));
		Thread threadCln = new Thread(( ) -> SSLSocketClient.main(args));

		threadSrv.start();
		threadCln.start();

		try {
			threadSrv.join();
			threadCln.join();
		}
		catch (InterruptedException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		System.out.println("DONE");
		assertNotNull(args);
	}
}
