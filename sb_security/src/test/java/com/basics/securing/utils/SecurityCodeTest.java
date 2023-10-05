package com.basics.securing.utils;

import com.sun.security.auth.callback.TextCallbackHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
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
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.securing.utils.SecurityCode.KEYSTORE_FILE11;
import static com.basics.securing.utils.SecurityCode.KEYSTORE_FILE17;
import static com.basics.securing.utils.SecurityCode.KEYSTORE_SECRET;
import static com.basics.securing.utils.SecurityCode.TRUSTSTORE_SECRET;
import static com.basics.securing.utils.SecurityCode.getAuthorization;
import static com.basics.securing.utils.SecurityCode.getCertificate;
import static com.basics.securing.utils.SecurityCode.getCertsTruststore;
import static com.basics.securing.utils.SecurityCode.getKeyManagers;
import static com.basics.securing.utils.SecurityCode.getSSLContext;
import static com.basics.securing.utils.SecurityCode.getSSLContextApache;
import static com.basics.securing.utils.SecurityCode.getTrustManagers;
import static com.basics.securing.utils.UtilityMain.EOL;
import static com.basics.securing.utils.UtilityMain.exposeObject;
import static com.basics.securing.utils.UtilityMainTest.PATHRESOURCE_MAIN;
import static com.basics.securing.utils.UtilityMainTest.PATHRESOURCE_TEST;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityCodeTest {

	public static final String[] KEYSTORE_JDK = { "jdk-11.0.12", "jdk-17.0.1", "jre1.8.0_301" };

	@Test void test_UUID( ) {

		StringBuilder sb = new StringBuilder();

		String uuid = UUID.randomUUID().toString();
		String uuidStripped = uuid.replace("-", "");
		sb.append("uuid: " + uuid + EOL);
		sb.append("uuidStripped: " + uuidStripped + EOL);

		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test void test_LoginContext( ) {

		StringBuilder sb = new StringBuilder();
		// https://www.demo2s.com/java/java-logincontext-logincontext-string-name-callbackhandler-callbackha.html
		LoginContext loginContext;
		Subject subject = new Subject();
		CallbackHandler callbackHandler =
			new TextCallbackHandler(); // DialogCallbackHandler, TextCallbackHandler
		Configuration configuration = Configuration.getConfiguration();
		try {
			loginContext = new LoginContext("sample", subject, callbackHandler, configuration);
			loginContext.login();
			sb.append("Authenticated!");
		}
		catch (LoginException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test void test_getAuthorization( ) {

		StringBuilder sb = new StringBuilder();
		String username = "username";
		String password = "password";
		String FRMT = "\t%s %s\n";
		sb.append(String.format(FRMT, "username/password", username + "/" + password));

		String encoded = getAuthorization(username, password);
		byte[] bytes = Base64.getDecoder().decode(encoded.substring(6));
		String decoded = new String(bytes);

		sb.append(String.format(FRMT, "encoded", encoded));
		sb.append(String.format(FRMT, "decoded", decoded));
		System.out.println(sb);
		assertNotNull(decoded);
	}

	@Test void test_SecureRandom( ) {

		StringBuilder sb = new StringBuilder();
		String FRMT = "%-20s %s\n";

		SecureRandom secureRandom = null;
		try {
			secureRandom = SecureRandom.getInstanceStrong();
			sb.append(String.format(FRMT, "provider", secureRandom.getProvider().getName()));
			sb.append(String.format(FRMT, "algorithm", secureRandom.getAlgorithm()));
			sb.append(StringUtils.repeat('-', 40) + EOL);
			//
			Instant instant = Instant.now();
			String seed = String.valueOf(instant.getNano());
			byte[] bytes = seed.getBytes();
			secureRandom = new SecureRandom(bytes);
			sb.append(String.format(FRMT, "provider", secureRandom.getProvider().getName()));
			sb.append(String.format(FRMT, "algorithm", secureRandom.getAlgorithm()));
			sb.append(String.format(FRMT, "seed", seed));
			sb.append(StringUtils.repeat('-', 40) + EOL);
			//
			sb.append(String.format(FRMT, "nextInt", secureRandom.nextInt()));
			sb.append(String.format(FRMT, "nextLong", secureRandom.nextLong()));
			sb.append(String.format(FRMT, "nextFloat", secureRandom.nextFloat()));
			sb.append(String.format(FRMT, "nextDouble", secureRandom.nextDouble()));
			sb.append(String.format(FRMT, "nextBoolean", secureRandom.nextBoolean()));
		}
		catch (NoSuchAlgorithmException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(sb);
		assertNotNull(secureRandom);
	}

	// keystore
	@Test void test_KeyStoreAliases( ) {

		StringBuilder sb = new StringBuilder();
		// https://www.baeldung.com/java-security-overview
		String keystoreFile = PATHRESOURCE_MAIN + KEYSTORE_FILE11;
		char[] keyStorePassword = KEYSTORE_SECRET.toCharArray();
		String FRMT = "\t%s %s\n";

		Enumeration<String> aliasEnumeration = null;
		try {
			// get keyStore
			String keyStoreDefaultType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreDefaultType);
			InputStream inputStream_KeystoreFile = new FileInputStream(keystoreFile);
			keyStore.load(inputStream_KeystoreFile, keyStorePassword);

			sb.append(String.format(FRMT, "keyStore.getType()", keyStore.getType()));
			sb.append(String.format(FRMT, "keyStore.getProvider()", keyStore.getProvider()));

			// handle keyStore Enumeration Iterator
			aliasEnumeration = keyStore.aliases();
			Iterator<String> aliasIterator = aliasEnumeration.asIterator();
			int ictr = 0;
			while ( aliasIterator.hasNext() ) {
				sb.append(String.format("\t%02d %s\n", ++ictr, aliasIterator.next()));
			}
			sb.append(String.format("\t%s %d\n", "ictr", ictr));
		}
		catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(sb);
		assertNotNull(aliasEnumeration);
	}

	@Test void test_getKeyManagers( ) {

		StringBuilder sb = new StringBuilder();
		// C:/Program Files/Java/jdk-17.0.1/lib/security
		String keystorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		KeyManager[] keyManagers = getKeyManagers(keystorePath, KEYSTORE_SECRET);
		Arrays.stream(keyManagers).forEach(keyManager ->
			sb.append(exposeObject(keyManager))
		);
		System.out.println(sb);
		assertNotNull(keyManagers);
	}

	@Test void test_getTrustManagers( ) {

		StringBuilder sb = new StringBuilder();
		String truststorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		TrustManager[] trustManagers = getTrustManagers(truststorePath, KEYSTORE_SECRET);
		Arrays.stream(trustManagers).forEach(trustManager ->
			sb.append(exposeObject(trustManager))
		);
		System.out.println(sb);
		assertNotNull(trustManagers);
	}

	@Test void test_getTrustManagers_certs( ) {

		StringBuilder sb = new StringBuilder();
		String truststorePath = PATHRESOURCE_MAIN + KEYSTORE_FILE17;

		TrustManager[] trustManagers = getTrustManagers(truststorePath, KEYSTORE_SECRET);
		List<TrustManager> trustManagersList = Arrays.asList(trustManagers);
		AtomicInteger ai = new AtomicInteger();
		int MAXLEN = 120;
		List.of(trustManagersList).forEach(trustManager -> {
			sb.append(trustManager.get(0).toString() + EOL);
			X509TrustManager x509TrustManager = (X509TrustManager) trustManagersList.get(ai.get());
			X509Certificate[] x509Certificates = x509TrustManager.getAcceptedIssuers();
			Arrays.stream(x509Certificates).sorted(Comparator.comparing(String::valueOf))
				.forEach(x509Certificate -> {
					String dnNames = x509Certificate.getSubjectDN().getName();
					if ( dnNames.length() > MAXLEN ) { dnNames = dnNames.substring(0, MAXLEN); }
					sb.append(String.format("\t%02d %s\n", ai.incrementAndGet(), dnNames));
				});
		});
		System.out.println(sb);
		assertNotNull(trustManagers);
	}

	@Test void test_getSSLContext( ) {

		StringBuilder sb = new StringBuilder();
		String[] props =
			{ PATHRESOURCE_MAIN + KEYSTORE_FILE17, KEYSTORE_SECRET, PATHRESOURCE_MAIN + KEYSTORE_FILE17,
				TRUSTSTORE_SECRET };

		SSLContext sslContext = getSSLContext(props);
		sb.append(exposeObject(sslContext));

		System.out.println(sb);
		assertNotNull(sslContext);
	}

	@Test @Disabled( "INCOMPLETE" ) void test_getSSLContextApache( ) {

		StringBuilder sb = new StringBuilder();
		String[] props = { "file://" + PATHRESOURCE_MAIN + KEYSTORE_FILE17, KEYSTORE_SECRET,
			"file://" + PATHRESOURCE_MAIN + KEYSTORE_FILE17, TRUSTSTORE_SECRET };

		SSLContext sslContext = getSSLContextApache(props);
		sb.append(exposeObject(sslContext));

		System.out.println(sb);
		assertNotNull(sslContext);
	}

	@Test void test_getCertificate( ) {

		StringBuilder sb = new StringBuilder();
		String filename = PATHRESOURCE_TEST + "samplecert.cer";
		X509Certificate x509Certificate = getCertificate(filename);

		sb.append("getSubjectDN: " + x509Certificate.getSubjectDN().getName() + EOL);
		sb.append("getIssuerDN: " + x509Certificate.getIssuerDN().getName() + EOL);
		sb.append("getIssuerX500Principal: " + x509Certificate.getIssuerX500Principal().getName() + EOL);
		sb.append("getType: " + x509Certificate.getType() + EOL);
		sb.append("getSigAlgName: " + x509Certificate.getSigAlgName() + EOL);
		sb.append("getSigAlgOID: " + x509Certificate.getPublicKey().toString() + EOL);
		sb.append("getSerialNumber: " + x509Certificate.getSerialNumber() + EOL);

		System.out.println(sb);
		assertNotNull(x509Certificate);
	}

	@Test void test_getCertsTruststore( ) {

		// https://www.baeldung.com/java-list-trusted-certificates
		StringBuilder sb = new StringBuilder();
		String pathTruststore = PATHRESOURCE_MAIN + KEYSTORE_FILE11;

		List<X509Certificate> certificates = getCertsTruststore(pathTruststore, TRUSTSTORE_SECRET);
		Set<String> set = new TreeSet<>();
		int LEN = 40;
		certificates.forEach(certificate -> {
			String SDN = certificate.getSubjectDN().getName();
			String IDN = certificate.getIssuerDN().getName();
			String PKA = certificate.getPublicKey().getAlgorithm();
			if ( SDN.length() < LEN ) { SDN = StringUtils.rightPad(SDN, LEN); } else {
				SDN = SDN.substring(0, LEN);
			}
			if ( IDN.length() < LEN ) { IDN = StringUtils.rightPad(IDN, LEN); } else {
				IDN = IDN.substring(0, LEN);
			}
			set.add(String.format("sb: %s, is: %s, al: %s", SDN, IDN, PKA));
		});
		AtomicInteger ai = new AtomicInteger();
		set.forEach(details -> sb.append(String.format("\t%02d %s\n", ai.incrementAndGet(), details)));
		System.out.println(sb);
		assertNotNull(certificates);
	}
}
