package com.basics.securing.utils;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.function.Supplier;

import static com.basics.securing.utils.SecurityCode.KEYSTORE_INSTANCE;
import static org.springframework.http.HttpMethod.GET;

public class SSLExchange {

	public static final String PATH_LOCAL = "C:/workspace/training/";
	public static final String SERVER_KEYSTORE = PATH_LOCAL + "serverkeystore.p12";
	public static final String SERVER_TRUSTSTORE = PATH_LOCAL + "servertruststore.jks";
	public static final String SERVER_CERTIFICATE = PATH_LOCAL + "server-certificate.pem";
	public static final String CLIENT_KEYSTORE = PATH_LOCAL + "clientkeystore.p12";
	public static final String CLIENT_TRUSTSTORE = PATH_LOCAL + "clienttruststore.jks";
	public static final String CLIENT_CERTIFICATE = PATH_LOCAL + "client-certificate.pem";

	public static final String PASSWORD_KEYS = "password";
	public static final String PASSWORD_TRST = "password";
	public static String getExchangeOneWay(String pathfileKey, String password, String url) {

		char[] charsPassword = password.toCharArray();

		KeyStore keyStore = getKeyStore(pathfileKey, charsPassword);
		SSLContext sslContext = getSSLContext(keyStore, charsPassword);
		HttpClient httpClient = getHttpClient(sslContext); // apache
		RestTemplate restTemplate = getRestTemplate(httpClient); // springframework
		ResponseEntity<String> responseEntity = getResponseEntity(url, restTemplate);

		return responseEntity.getBody();
	}

	private static KeyStore getKeyStore(String pathFileKey, char[] charsPassword) {

		KeyStore keyStore = null;
		try {
			InputStream inputStream = new FileInputStream(pathFileKey);
			keyStore = KeyStore.getInstance(KEYSTORE_INSTANCE);
			keyStore.load(inputStream, charsPassword);
		}
		catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return keyStore;
	}

	private static SSLContext getSSLContext(KeyStore keyStore, char[] charsPassword) {

		SSLContext sslContext = null;
		try {
			TrustSelfSignedStrategy TSSS = new TrustSelfSignedStrategy();
			KeyStore truststore = null;
			sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(keyStore, charsPassword)
				.loadTrustMaterial(truststore, TSSS)
				.build();
		}
		catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException |
		       KeyManagementException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return sslContext;
	}

	public static HttpClient getHttpClient(SSLContext sslContext) {

		// apache classes
		SSLConnectionSocketFactory SSL_CSF = SSLConnectionSocketFactoryBuilder.create()
			.setSslContext(sslContext)
			.build();
		HttpClientConnectionManager HCCM = PoolingHttpClientConnectionManagerBuilder.create()
			.setSSLSocketFactory(SSL_CSF)
			.build();
		HttpClient httpClient
			= HttpClients.custom().setConnectionManager(HCCM)
			.build();

		return httpClient;
	}

	private static RestTemplate getRestTemplate(HttpClient httpClient) {

		// springframework classes
		ClientHttpRequestFactory CHCR = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		restTemplateBuilder.requestFactory(() -> CHCR).build();
		SSLSupplierRequestFactoryImpl SSRFI = new SSLSupplierRequestFactoryImpl(); // Supplier
		RestTemplate restTemplate = restTemplateBuilder.requestFactory(SSRFI).build();

		return restTemplate;
	}

	private static ResponseEntity<String> getResponseEntity(String url, RestTemplate restTemplate) {

		URI uri = null;
		try { uri = new URI(url); }
		catch (URISyntaxException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		ResponseEntity<String> responseEntity = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>(null, httpHeaders);
		responseEntity = restTemplate.exchange(Objects.requireNonNull(uri), GET, httpEntity, String.class);
		return responseEntity;
	}
}

class SSLSupplierRequestFactoryImpl implements Supplier<ClientHttpRequestFactory> {

	@Override
	public ClientHttpRequestFactory get() {

		// apache
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();

		// springframework
		HttpComponentsClientHttpRequestFactory HCC_HRF = new HttpComponentsClientHttpRequestFactory(httpClient);
		HCC_HRF.setBufferRequestBody(false); // for big POST data use false to save memory
		return HCC_HRF;
	}
}