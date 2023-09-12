package com.basics.securing.utils;

import com.sun.security.auth.callback.TextCallbackHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.securing.utils.UtilityMain.EOL;
import static com.basics.securing.utils.UtilityMain.exposeObject;
import static com.basics.securing.utils.UtilityMainTest.PATHRESOURCE_MAIN;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityCodeTest {

	public static final String KEYSTORE_FILE = "cacerts";
	public static final String KEYSTORE_FILE17 = "cacerts17";
	public static final String KEYSTORE_SECRET = "changeit";
	public static final String KEYSTORE_PATHTEMPLATE = "C:/Program Files/Java/*/lib/security/";
	public static final String[] KEYSTORE_JDK = { "jdk-11.0.12", "jdk-17.0.1", "jre1.8.0_301" };

	@Test void getUUID( ) {

		StringBuilder stringBuilder = new StringBuilder();

		String uuid = UUID.randomUUID().toString();
		String uuidStripped = uuid.replace("-", "");
		stringBuilder.append("uuid: " + uuid + EOL);
		stringBuilder.append("uuidStripped: " + uuidStripped + EOL);

		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void getLoginContext( ) {

		StringBuilder stringBuilder = new StringBuilder();
		// https://www.demo2s.com/java/java-logincontext-logincontext-string-name-callbackhandler-callbackha.html
		LoginContext loginContext;
		Subject subject = new Subject();
		CallbackHandler callbackHandler = new TextCallbackHandler(); // DialogCallbackHandler, TextCallbackHandler
		Configuration configuration = Configuration.getConfiguration();
		try {
			loginContext = new LoginContext("sample", subject, callbackHandler, configuration);
			loginContext.login();
			stringBuilder.append("Authenticated!");
		}
		catch (LoginException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void getAuthorization( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String username = "username";
		String password = "password";
		String FRMT = "\t%s %s\n";
		stringBuilder.append(String.format(FRMT, "username/password", username + "/" + password));

		String encoded = SecurityCode.getAuthorization(username, password);
		byte[] bytes = Base64.getDecoder().decode(encoded.substring(6));
		String decoded = new String(bytes);

		stringBuilder.append(String.format(FRMT, "encoded", encoded));
		stringBuilder.append(String.format(FRMT, "decoded", decoded));
		System.out.println(stringBuilder);
		assertNotNull(decoded);
	}

	@Test void getSecureRandom( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String FRMT = "%-20s %s\n";

		SecureRandom secureRandom = null;
		try {
			secureRandom = SecureRandom.getInstanceStrong();
			stringBuilder.append(String.format(FRMT, "provider", secureRandom.getProvider().getName()));
			stringBuilder.append(String.format(FRMT, "algorithm", secureRandom.getAlgorithm()));
			stringBuilder.append(StringUtils.repeat('-', 40) + EOL);
			//
			Instant instant = Instant.now();
			String seed = String.valueOf(instant.getNano());
			byte[] bytes = seed.getBytes();
			secureRandom = new SecureRandom(bytes);
			stringBuilder.append(String.format(FRMT, "provider", secureRandom.getProvider().getName()));
			stringBuilder.append(String.format(FRMT, "algorithm", secureRandom.getAlgorithm()));
			stringBuilder.append(String.format(FRMT, "seed", seed));
			stringBuilder.append(StringUtils.repeat('-', 40) + EOL);
			//
			stringBuilder.append(String.format(FRMT, "nextInt", secureRandom.nextInt()));
			stringBuilder.append(String.format(FRMT, "nextLong", secureRandom.nextLong()));
			stringBuilder.append(String.format(FRMT, "nextFloat", secureRandom.nextFloat()));
			stringBuilder.append(String.format(FRMT, "nextDouble", secureRandom.nextDouble()));
			stringBuilder.append(String.format(FRMT, "nextBoolean", secureRandom.nextBoolean()));
		}
		catch (NoSuchAlgorithmException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(stringBuilder);
		assertNotNull(secureRandom);
	}

	// keystore
	@Test void getKeyStoreAliases( ) {

		StringBuilder stringBuilder = new StringBuilder();
		// https://www.baeldung.com/java-security-overview
		String keystoreFile = KEYSTORE_PATHTEMPLATE.replace("*",KEYSTORE_JDK[0]) + KEYSTORE_FILE; // "cacerts", "keystore.jks"
		char[] keyStorePassword = KEYSTORE_SECRET.toCharArray();
		String FRMT = "\t%s %s\n";

		Enumeration<String> aliasEnumeration = null;
		try {
			// get keyStore
			String keyStoreDefaultType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreDefaultType);
			InputStream inputStream_KeystoreFile = new FileInputStream(keystoreFile);
			keyStore.load(inputStream_KeystoreFile, keyStorePassword);

			stringBuilder.append(String.format(FRMT, "keyStore.getType()", keyStore.getType()));
			stringBuilder.append(String.format(FRMT, "keyStore.getProvider()", keyStore.getProvider()));

			// handle keyStore Enumeration Iterator
			aliasEnumeration = keyStore.aliases();
			Iterator<String> aliasIterator = aliasEnumeration.asIterator();
			int ictr = 0;
			while ( aliasIterator.hasNext() ) {
				stringBuilder.append(String.format("\t%02d %s\n", ++ictr, aliasIterator.next()));
			}
			stringBuilder.append(String.format("\t%s %d\n", "ictr", ictr));
		}
		catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(stringBuilder);
		assertNotNull(aliasEnumeration);
	}

	@Test void getKeyManagers( ) {

		StringBuilder stringBuilder = new StringBuilder();
		// C:/Program Files/Java/jdk-17.0.1/lib/security
		String keystorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		KeyManager[] keyManagers = SecurityCode.getKeyManagers(keystorePath, KEYSTORE_SECRET);
		Arrays.stream(keyManagers).forEach(keyManager ->
			stringBuilder.append(exposeObject(keyManager))
		);
		System.out.println(stringBuilder);
		assertNotNull(keyManagers);
	}

	@Test void getTrustManagers( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String truststorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		TrustManager[] trustManagers = SecurityCode.getTrustManagers(truststorePath, KEYSTORE_SECRET);
		Arrays.stream(trustManagers).forEach(trustManager ->
			stringBuilder.append(exposeObject(trustManager))
		);
		System.out.println(stringBuilder);
		assertNotNull(trustManagers);
	}

	@Test void getTrustManagers_certs( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String truststorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		TrustManager[] trustManagers = SecurityCode.getTrustManagers(truststorePath, KEYSTORE_SECRET);
		List<TrustManager> trustManagersList = Arrays.asList(trustManagers);
		AtomicInteger ai = new AtomicInteger();
		int MAXLEN = 120;
		List.of(trustManagersList).forEach(trustManager -> {
			stringBuilder.append(trustManager.get(0).toString() + EOL);
			X509TrustManager x509TrustManager = (X509TrustManager) trustManagersList.get(ai.get());
			X509Certificate[] x509Certificates = x509TrustManager.getAcceptedIssuers();
			Arrays.stream(x509Certificates).sorted(Comparator.comparing(String::valueOf))
				.forEach(x509Certificate -> {
					String dnNames = x509Certificate.getSubjectDN().getName();
					if(dnNames.length()>MAXLEN) {dnNames = dnNames.substring(0,MAXLEN);}
					stringBuilder.append(String.format("\t%02d %s\n",ai.incrementAndGet(),dnNames));
				});
		});
		System.out.println(stringBuilder);
		assertNotNull(trustManagers);
	}

	@Test void getSSLContext( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String keystorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		SSLContext sslContext = SecurityCode.getSSLContext(keystorePath, KEYSTORE_SECRET);
		stringBuilder.append(exposeObject(sslContext));

		System.out.println(stringBuilder);
		assertNotNull(sslContext);
	}
}
