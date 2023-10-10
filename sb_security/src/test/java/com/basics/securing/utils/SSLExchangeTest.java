package com.basics.securing.utils;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
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
import static com.basics.securing.utils.SSLExchange.getSSLContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.CONTINUE;

@Disabled( "INCOMPLETE" )
class SSLExchangeTest {

	private static final String[] URL_EXCHANGES =
		{ "https://localhost:80", "localhost", "127.0.0.1", "http://httpbin.org" };

	// 1way SSLContext
	@Test void test_getHttpClient( ) {

		String urlExchange = URL_EXCHANGES[0];
		char[] charsPassword = PASSWORD_TRST.toCharArray();
		String urlClientTruststore = CLIENT_TRUSTSTORE;

		URL urlTrustStore = null;
		try { urlTrustStore = new URL("file:///" + urlClientTruststore); }
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

		ResponseEntity<String> responseEntity = new ResponseEntity<>("EMPTY", CONTINUE);
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>("anybody", httpHeaders);
		try { responseEntity = restTemplate.exchange(urlExchange, GET, httpEntity, String.class);
		} catch (ResourceAccessException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		String txtlines = responseEntity.getBody();
		System.out.println(txtlines);
		assertNotNull(txtlines);
	}

	// 1way SSLContext
	@Test void test_getHttpClient_props( ) {

		String urlExchange = URL_EXCHANGES[0];
		String[] props = {CLIENT_KEYSTORE,PASSWORD_KEYS,CLIENT_TRUSTSTORE,PASSWORD_TRST};

		SSLContext sslContext = getSSLContext(props);
		HttpClient httpClient = getHttpClient(sslContext);
		ClientHttpRequestFactory CHRF = new HttpComponentsClientHttpRequestFactory(httpClient);

		RestTemplate restTemplate = new RestTemplate(CHRF);

		ResponseEntity<String> responseEntity = new ResponseEntity<>("EMPTY", CONTINUE);
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>("anybody", httpHeaders);
		try { responseEntity = Objects.requireNonNull(restTemplate).exchange(urlExchange, GET, httpEntity, String.class);
		} catch (ResourceAccessException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		String txtlines = responseEntity.getBody();
		System.out.println(txtlines);
		assertNotNull(txtlines);
	}

	// 1way SSLExchange
	@Test void test_1Way_getExchangeOneWay( ) {

		String txtlines = getExchangeOneWay(CLIENT_TRUSTSTORE, PASSWORD_TRST, URL_EXCHANGES[0]);
		System.out.println(txtlines);
		assertNotNull(txtlines);
	}

	// 2Way SSLSockets
	@Test void test_SSLSockets( ) {

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
