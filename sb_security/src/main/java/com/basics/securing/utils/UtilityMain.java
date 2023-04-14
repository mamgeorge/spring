package com.basics.securing.utils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class UtilityMain {

	public static final Logger LOGGER = Logger.getLogger(UtilityMain.class.getName());

	public static final String EOL = "\n";

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

	public static SSLContext getSSLSocketFactory(String keystorePath, String keystoreSecret) {

		SSLContext sslContext = null;
		String SSLCONTEXT_INSTANCE = "TLSv1.2";
		String KEYSTORE_INSTANCE = "JKS";

		String KDA = KeyManagerFactory.getDefaultAlgorithm();
		System.out.println("keystorePath: " + keystorePath + ", keystoreDefaultAlgorithm: " + KDA);
		try {
			File fileKeystore = new File(keystorePath);
			InputStream inputStream = new FileInputStream(fileKeystore);
			KeyStore keyStore = KeyStore.getInstance(KEYSTORE_INSTANCE);
			keyStore.load(inputStream, keystoreSecret.toCharArray());

			KeyManagerFactory KMF = KeyManagerFactory.getInstance(KDA);
			KMF.init(keyStore, keystoreSecret.toCharArray());
			System.out.println("KMF provider: " + KMF.getProvider().toString());

			KeyManager[] keyManagers = KMF.getKeyManagers();
			TrustManager[] trustManagers = null;
			SecureRandom secureRandom = null;
			Arrays.asList(keyManagers).stream().forEach( keyManager ->
				System.out.println(exposeObject(keyManager)));
			sslContext = SSLContext.getInstance(SSLCONTEXT_INSTANCE);
			sslContext.init(keyManagers, trustManagers, secureRandom);
			inputStream.close();
		}
		catch (KeyStoreException | FileNotFoundException | NoSuchAlgorithmException | CertificateException |
		       UnrecoverableKeyException | KeyManagementException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return sslContext;
	}
}
