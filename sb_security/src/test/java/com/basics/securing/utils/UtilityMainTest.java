package com.basics.securing.utils;

import com.sun.security.auth.callback.TextCallbackHandler;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;

import static com.basics.securing.utils.UtilityMain.EOL;
import static com.basics.securing.utils.UtilityMain.exposeObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilityMainTest {

	public static final String PATH_RESOURCES_TEST = "src/test/resources/";
	public static final String PATH_RESOURCES_MAIN = "src/main/resources/";

	@Test void showSys( ) {

		String txtLines = UtilityMain.showSys();
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void getSSLSocketFactory( ) {

		String keystorePath = PATH_RESOURCES_MAIN + "cacerts17";
		String keystoreSecret = "changeit";

		SSLContext sslContext = UtilityMain.getSSLSocketFactory(keystorePath, keystoreSecret);
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
