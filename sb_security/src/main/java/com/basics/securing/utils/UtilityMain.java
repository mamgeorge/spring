package com.basics.securing.utils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class UtilityMain {

	public static final Logger LOGGER = Logger.getLogger(UtilityMain.class.getName());

	public static final String EOL = "\n";
	public static final String SSLCONTEXT_INSTANCE = "TLSv1.2";
	public static final String KEYSTORE_ALGORITHM = "JKS";

	//#### basics
	public static String showSys( ) {
		//
		Map<String, String> mapEnv = System.getenv();
		Map<String, String> mapEnvTree = new TreeMap<>(mapEnv);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		AtomicInteger aint = new AtomicInteger();
		mapEnvTree.forEach((key, val) -> {
			//
			val = val.replace("\\", "/");
			val = val.replace("\"", "'");
			// stringBuffer.append("{\"" + key + "\":\"" + val + "\"},");
			stringBuilder.append(String.format("\t%03d %-20s : %s\n", aint.incrementAndGet(), key, val));
		});
		stringBuilder.append("]\n");
		stringBuilder.append("\tUSERNAME: ").append(System.getenv("USERNAME")).append(EOL);
		return stringBuilder.toString();
	}

	public static String getFileLocal(String pathFile) {
		//
		// https://howtodoinjava.com/java/io/read-file-from-resources-folder/
		// File file = ResourceUtils.getFile("classpath:config/sample.txt")
		String txtLines = "";
		try {
			File fileLocal = new File(pathFile);
			File pathFileLocal = new File(fileLocal.getAbsolutePath());
			txtLines = Files.readString(pathFileLocal.toPath());
		}
		catch (IOException | NullPointerException ex) {
			LOGGER.warning(ex.getMessage());
		}
		return txtLines;
	}

	public static String exposeObject(Object object) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		Set<String> setLines = new TreeSet<>();
		Method[] methods = object.getClass().getDeclaredMethods();
		List<Method> listMethods = new ArrayList<>();
		Arrays.stream(methods).forEach(method -> listMethods.add(method));
		Collections.sort(listMethods, Comparator.comparing(Method::getName));
		//
		int MAXLEN = 35;
		AtomicInteger usedMethods = new AtomicInteger();
		String FRMT = "%-30s | %-35s | %02d | %s \n";
		listMethods.forEach(method -> {
			//
			String methodName = method.getName();
			boolean boolAccess = methodName.startsWith("access$") | methodName.startsWith("$$$");
			if ( !boolAccess ) {
				usedMethods.incrementAndGet();
				Object objectVal = "";
				String returnType = method.getReturnType().toString();
				if ( returnType.length() > MAXLEN ) {
					returnType = returnType.substring(returnType.length() - MAXLEN);
				}
				method.setAccessible(true);
				Object[] args;
				if ( method.getParameterCount() > 0 ) {
					if ( method.getParameterTypes()[0].getName().contains("String") ) {
						args = new Object[]{ "RANDOM: " + getRandomString(8) };
					} else if ( method.getParameterTypes()[0].getName().contains("Date") ) {
						args = new Object[]{ new Date() };
					} else if ( method.getParameterTypes()[0].getName().contains("int") ) {
						args = new Object[]{ (int) Math.round(Math.random() * 4000) };
					} else {
						String parmname = method.getParameterTypes()[0].getName();
						args = new Object[]{ parmname };
					}
				} else { args = null; }
				try {
					objectVal = method.invoke(object, args);
					if ( objectVal == null ) {
						if ( method.getParameterCount() == 0 ) { objectVal = null; } else {
							objectVal = args[0];
						}
					}
				}
				catch (IllegalAccessException | InvocationTargetException ex) {
					LOGGER.info(methodName + " | " + ex.getMessage());
				}
				catch (IllegalArgumentException IAE) { objectVal = "REQUIRES: " + args[0]; }
				setLines.add(
					String.format(FRMT, methodName, returnType, method.getParameterCount(), objectVal));
			}
		});
		//
		stringBuilder.append(object.getClass().getName()).append(" has: [").append(usedMethods)
			.append("] methods\n\n");

		AtomicInteger atomicInteger = new AtomicInteger();
		setLines.stream().forEach(val -> stringBuilder.append(String.format("\t %02d %s",
			atomicInteger.incrementAndGet(), val)));
		return stringBuilder + EOL;
	}

	public static ConfigurableEnvironment getEnvironment( ) {

		// context comes from webmvc starter; may be included in others
		AnnotationConfigApplicationContext ACAC = new AnnotationConfigApplicationContext();
		ConfigurableEnvironment environment = ACAC.getEnvironment();
		MutablePropertySources mutablePropertySources = environment.getPropertySources();

		Map<String, Object> map = new HashMap<>();
		map.put("any.prop.path", "anyproperty");
		map.put("spring.application.id", "MLG");

		MapPropertySource mapPropertySource = new MapPropertySource("testEnvironment", map);
		mutablePropertySources.addFirst(mapPropertySource);
		return environment;
	}

	public static String getRandomString(int num) {
		//
		StringBuilder txtRandom = new StringBuilder();
		Random random = new Random();
		char[] chars =
			( "1234567890abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWZYZ" ).toCharArray();
		for ( int ictr = 0; ictr < num; ictr++ ) {
			txtRandom.append(chars[random.nextInt(chars.length)]);
		}
		return txtRandom.toString();
	}

	// security
	public static String getAuthorization(String username, String password) {

		String userpass = username + ":" + password;
		String userpassEncoded = Base64.getEncoder().encodeToString(userpass.getBytes(UTF_8));
		String authEncoded = "Basic " + userpassEncoded;
		return authEncoded;
	}

	public static KeyManager[] getKeyManagers(String keystorePath, String keystoreSecret) {

		// KeyManager checks which authentication credentials should be sent to remote host
		String keystoreAlgorithmDEF = KeyManagerFactory.getDefaultAlgorithm();
		String keyStoreDefaultType = KeyStore.getDefaultType();
		System.out.println("keystorePath: " + keystorePath
			+ "\n\t keyStoreDefaultType: " + keyStoreDefaultType
			+ "\n\t keystoreAlgorithmDEF: " + keystoreAlgorithmDEF
			+ "\n\t keystoreAlgorithm: " + KEYSTORE_ALGORITHM);
		String keystoreAlgorithm = KEYSTORE_ALGORITHM;

		KeyManager[] keyManagers = null;
		try {
			// get keystore from file
			File fileKeystore = new File(keystorePath);
			InputStream inputStream = new FileInputStream(fileKeystore);
			KeyStore keyStore = KeyStore.getInstance(keystoreAlgorithm);
			keyStore.load(inputStream, keystoreSecret.toCharArray());

			// start KMF to get keyManagers
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keystoreAlgorithmDEF);
			keyManagerFactory.init(keyStore, keystoreSecret.toCharArray());
			System.out.println("\t keyManagerFactory provider: " + keyManagerFactory.getProvider().toString());
			keyManagers = keyManagerFactory.getKeyManagers();
		}
		catch (FileNotFoundException | KeyStoreException | NoSuchAlgorithmException |
		       UnrecoverableKeyException | CertificateException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return keyManagers;
	}

	public static TrustManager[] getTrustManagers(String truststorePath, String truststoreSecret) {

		// TrustManager checks if remote connection should be trusted, if remote party is who it claims to be
		String truststoreAlgorithmDEF = TrustManagerFactory.getDefaultAlgorithm();
		String keyStoreDefaultType = KeyStore.getDefaultType();
		System.out.println("truststorePath: " + truststorePath
			+ "\n\t keyStoreDefaultType: " + keyStoreDefaultType
			+ "\n\t truststoreAlgorithmDEF: " + truststoreAlgorithmDEF
			+ "\n\t truststoreAlgorithm: " + keyStoreDefaultType);
		String truststoreAlgorithm = keyStoreDefaultType;

		TrustManager[] trustManagers = null;
		try {
			// get trustStore from file
			File fileTruststore = new File(truststorePath);
			InputStream inputStream = new FileInputStream(fileTruststore);
			KeyStore trustStore = KeyStore.getInstance(truststoreAlgorithm);
			trustStore.load(inputStream, truststoreSecret.toCharArray());

			// start TMF to get trustManagers
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(truststoreAlgorithmDEF);
			trustManagerFactory.init(trustStore);
			trustManagers = trustManagerFactory.getTrustManagers();
		}
		catch (FileNotFoundException | KeyStoreException | NoSuchAlgorithmException |
		       CertificateException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return trustManagers;
	}

	public static SSLContext getSSLContext(String keystorePath, String keystoreSecret) {

		// C:/Program Files/Java/jdk-17.0.1/lib/security
		SSLContext sslContext = null;
		try {
			// inititalize keyManagers, trustManagers, & secureRandom
			KeyManager[] keyManagers = getKeyManagers(keystorePath, keystoreSecret);
			TrustManager[] trustManagers = getTrustManagers(keystorePath, keystoreSecret);
			SecureRandom secureRandom = SecureRandom.getInstanceStrong();

			// get sslContext from keyManagers, trustManagers, & secureRandom
			sslContext = SSLContext.getInstance(SSLCONTEXT_INSTANCE);
			sslContext.init(keyManagers, trustManagers, secureRandom);
		}
		catch (NoSuchAlgorithmException | KeyManagementException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return sslContext;
	}
}
