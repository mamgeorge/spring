package com.basics.securing.utils;

import com.sun.security.auth.callback.TextCallbackHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.security.auth.callback.CallbackHandler;
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
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static com.basics.securing.utils.UtilityMain.EOL;
import static com.basics.securing.utils.UtilityMain.exposeObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilityMainTest {

	public static final String PATH_RESOURCES_TEST = "src/test/resources/";
	public static final String PATH_RESOURCES_MAIN = "src/main/resources/";
	public static final String KEYSTORE_FILE = "cacerts17";
	public static final String KEYSTORE_SECRET = "changeit";

	@BeforeAll static void init( ) {

		System.out.println("enable --illegal-access" + EOL);
		// --add-opens java.base/java.lang=ALL-UNNAMED
		// --illegal-access=permit
	}

	@Test void showSys( ) {

		String txtLines = UtilityMain.showSys();
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void getEnvironment( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment environment = UtilityMain.getEnvironment();

		Map<String, Object> mapSystemEnv = environment.getSystemEnvironment();
		stringBuilder.append(EOL + "mapSystemEnv: " + mapSystemEnv.size() + EOL);
		mapSystemEnv.forEach(
			(key, val) -> { stringBuilder.append(String.format(FRMT, key, val.toString())); });

		Map<String, Object> mapSystemProps = environment.getSystemProperties();
		stringBuilder.append(EOL + "mapSystemProps: " + mapSystemProps.size() + EOL);
		mapSystemProps.forEach(
			(key, val) -> { stringBuilder.append(String.format(FRMT, key, val.toString())); });

		String[] activeProfiles = environment.getActiveProfiles();
		stringBuilder.append(EOL + "activeProfiles: " + activeProfiles.length + EOL);
		Arrays.stream(activeProfiles)
			.sorted()
			.forEach(activeProfile -> { stringBuilder.append(String.format(FRMT, activeProfile, "")); });

		String[] defaultProfiles = environment.getDefaultProfiles();
		stringBuilder.append(EOL + "defaultProfiles: " + defaultProfiles.length + EOL);
		Arrays.stream(defaultProfiles)
			.sorted()
			.forEach(defaultProfile -> { stringBuilder.append(String.format(FRMT, defaultProfile, "")); });

		stringBuilder.append(EOL + "getProperty" + EOL);
		stringBuilder.append(String.format(FRMT, "any.prop.path", environment.getProperty("any.prop.path")));
		stringBuilder.append(
			String.format(FRMT, "spring.application.id", environment.getProperty("spring.application.id")));
		stringBuilder.append(String.format(FRMT, "server.servlet.context-path",
			environment.getProperty("server.servlet.context-path")));

		System.out.println(stringBuilder);
		System.out.println(exposeObject(environment));

		assertNotNull(environment);
	}

	// security
	@Test void getAuthorization( ) {

		String encoded = UtilityMain.getAuthorization("username", "password");
		byte[] bytes = Base64.getDecoder().decode(encoded.substring(6));
		String decoded = new String(bytes);
		System.out.println("decoded: " + decoded);
		assertNotNull(decoded);
	}

	@Test void getKeyManagers( ) {

		// C:/Program Files/Java/jdk-17.0.1/lib/security
		String keystorePath = PATH_RESOURCES_MAIN + KEYSTORE_FILE;
		String keystoreSecret = KEYSTORE_SECRET;

		KeyManager[] keyManagers = UtilityMain.getKeyManagers(keystorePath, keystoreSecret);
		Arrays.asList(keyManagers).stream().forEach(keyManager ->
			System.out.println(exposeObject(keyManager)));

		assertNotNull(keyManagers);
	}

	@Test void getTrustManagers( ) {

		String truststorePath = PATH_RESOURCES_MAIN + KEYSTORE_FILE;
		String truststoreSecret = KEYSTORE_SECRET;

		TrustManager[] trustManagers = UtilityMain.getTrustManagers(truststorePath, truststoreSecret);
		Arrays.asList(trustManagers).stream().forEach(trustManager ->
			System.out.println(exposeObject(trustManager)));

		assertNotNull(trustManagers);
	}

	@Test void getSecureRandom( ) {

		String txtLines = "";
		String FRMT = "%-20s %s\n";
		try {
			SecureRandom secureRandom = null;
			secureRandom = SecureRandom.getInstanceStrong();
			txtLines += String.format(FRMT, "provider", secureRandom.getProvider().getName());
			txtLines += String.format(FRMT, "algorithm", secureRandom.getAlgorithm());
			txtLines += StringUtils.repeat('-', 40) + EOL;
			//
			Instant instant = Instant.now();
			String seed = String.valueOf(instant.getNano());
			byte[] bytes = seed.getBytes();
			secureRandom = new SecureRandom(bytes);
			txtLines += String.format(FRMT, "provider", secureRandom.getProvider().getName());
			txtLines += String.format(FRMT, "algorithm", secureRandom.getAlgorithm());
			txtLines += String.format(FRMT, "seed", seed);
			txtLines += StringUtils.repeat('-', 40) + EOL;
			//
			txtLines += String.format(FRMT, "nextInt", secureRandom.nextInt());
			txtLines += String.format(FRMT, "nextLong", secureRandom.nextLong());
			txtLines += String.format(FRMT, "nextFloat", secureRandom.nextFloat());
			txtLines += String.format(FRMT, "nextDouble", secureRandom.nextDouble());
			txtLines += String.format(FRMT, "nextBoolean", secureRandom.nextBoolean());
		}
		catch (NoSuchAlgorithmException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void getSSLContext( ) {

		String keystorePath = PATH_RESOURCES_MAIN + "cacerts17";
		String keystoreSecret = "changeit";

		SSLContext sslContext = UtilityMain.getSSLContext(keystorePath, keystoreSecret);
		System.out.println(exposeObject(sslContext));
		assertNotNull(sslContext);
	}

	//#### generic tests
	@Test void getUUID( ) {

		String txtLines = "";
		String uuid = UUID.randomUUID().toString();
		String uuidStripped = uuid.replace("-", "");
		txtLines += "uuid: " + uuid + EOL;
		txtLines += "uuidStripped: " + uuidStripped + EOL;
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void getKeyStoreAliases( ) {

		// https://www.baeldung.com/java-security-overview
		String txtLine = "", txtLines = "";
		String keyStorePasswordTxt = "changeit";
		char[] keyStorePassword = keyStorePasswordTxt.toCharArray();
		String[] keystorePath = {
			"C:/Program Files/Java/jdk-11.0.12/lib/security/",
			"C:/Program Files/Java/jdk-17.0.1/lib/security/",
			"C:/Program Files/Java/jre1.8.0_301/lib/security/"
		};
		String keystoreFile = keystorePath[0] + "cacerts"; // "cacerts", "keystore.jks"
		try {
			// get keyStore
			String keyStoreDefaultType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreDefaultType);
			InputStream keystoreFileInputStream = new FileInputStream(keystoreFile);
			keyStore.load(keystoreFileInputStream, keyStorePassword);

			txtLines += String.format("\t%s %s\n", "keyStore.getType()", keyStore.getType());
			txtLines += String.format("\t%s %s\n", "keyStore.getProvider()", keyStore.getProvider());

			// handle keyStore Enumeration Iterator
			Enumeration<String> aliasEnumeration = keyStore.aliases();
			Iterator<String> aliasIterator = aliasEnumeration.asIterator();
			int ictr = 0;
			while ( aliasIterator.hasNext() ) {
				txtLine = String.format("\t%02d %s\n", ++ictr, aliasIterator.next());
				// txtLines += txtLine;
			}
			txtLines += String.format("\t%s %d\n", "ictr", ictr);
		}
		catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void getLoginContext( ) {

		// https://www.demo2s.com/java/java-logincontext-logincontext-string-name-callbackhandler-callbackha.html
		try {
			CallbackHandler callbackHandler = new TextCallbackHandler(); // DialogCallbackHandler
			LoginContext loginContext = new LoginContext("Sample", callbackHandler);
			loginContext.login();
			System.out.println("Authenticated!");
		}
		catch (LoginException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}
}
