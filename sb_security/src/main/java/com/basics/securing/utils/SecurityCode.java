package com.basics.securing.utils;

import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import static com.basics.securing.utils.UtilityMain.EOL;
import static java.nio.charset.StandardCharsets.UTF_8;

public class SecurityCode {

	/*
		JSSE (Java Secure Socket Extension)
		Keystore file: keystore.jks password changeit
		Truststore file: %JAVA_HOME%/lib/cacerts.jks
	*/
	private SecurityCode( ) { }

	public static final String ENABLEDCIPHER_SUITES = "TLS_AES_128_GCM_SHA256";
	public static final String[] SSLCONTEXT_INSTANCES = {"TLSv1.2", "TLSv1.3"};
	public static final String KEYSTORE_ALGORITHM = "JKS";
	public static final String KEYSTORE_INSTANCE = "PKCS12";

	public static final String CACERTS = "cacerts";
	public static final String KEYSTORE_FILE11 = CACERTS + "11";
	public static final String KEYSTORE_FILE17 = CACERTS + "17";
	public static final String KEYSTORE_SECRET = "changeit";
	public static final String TRUSTSTORE_SECRET = "changeit";

	public static final String ERROR = "ERROR: ";
	public static final String FRMT = "\t%-25s %s\n";

	public static String getAuthorization(String username, String password) {

		StringBuilder stringBuilder = new StringBuilder();
		String userpass = username + ":" + password;
		String userpassEncoded = Base64.getEncoder().encodeToString(userpass.getBytes(UTF_8));
		String authEncoded = "Basic " + userpassEncoded;

		stringBuilder.append("userpass: ").append(userpass);
		stringBuilder.append("userpassEncoded: ").append(userpassEncoded);
		stringBuilder.append("authEncoded: ").append(authEncoded);
		System.out.println(stringBuilder);

		return authEncoded;
	}

	public static KeyManager[] getKeyManagers(String keystorePath, String keystoreSecret) {

		StringBuilder stringBuilder = new StringBuilder();
		// KeyManager checks which authentication credentials should be sent to remote host
		String keystoreAlgorithmDEF = KeyManagerFactory.getDefaultAlgorithm();
		String keyStoreDefaultType = KeyStore.getDefaultType();
		stringBuilder.append("keystorePath: " + keystorePath + EOL);
		stringBuilder.append(String.format(FRMT, "keyStoreDefaultType", keyStoreDefaultType));
		stringBuilder.append(String.format(FRMT, "keystoreAlgorithmDEF", keystoreAlgorithmDEF));
		stringBuilder.append(String.format(FRMT, "keystoreAlgorithm", KEYSTORE_ALGORITHM));

		KeyManager[] keyManagers = null;
		// get keystore from file
		File fileKeystore = new File(keystorePath);
		try ( InputStream inputStream = new FileInputStream(fileKeystore) ) {
			KeyStore keyStore = KeyStore.getInstance(KEYSTORE_ALGORITHM);
			keyStore.load(inputStream, keystoreSecret.toCharArray());

			// start KMF to get keyManagers
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keystoreAlgorithmDEF);
			keyManagerFactory.init(keyStore, keystoreSecret.toCharArray());
			stringBuilder.append(
				String.format(FRMT, "KMFactory provider", keyManagerFactory.getProvider().toString()));
			keyManagers = keyManagerFactory.getKeyManagers();
		}
		catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException |
		       CertificateException ex) {
			System.out.println(ERROR + ex.getMessage());
		}
		System.out.println(stringBuilder);
		return keyManagers;
	}

	public static TrustManager[] getTrustManagers(String truststorePath, String truststoreSecret) {

		StringBuilder stringBuilder = new StringBuilder();
		// TrustManager checks if remote connection should be trusted, if remote party is who it claims to be
		String truststoreAlgorithmDEF = TrustManagerFactory.getDefaultAlgorithm();
		String keyStoreDefaultType = KeyStore.getDefaultType();
		stringBuilder.append("truststorePath: " + truststorePath + EOL);
		stringBuilder.append(String.format(FRMT, "keyStoreDefaultType", keyStoreDefaultType));
		stringBuilder.append(String.format(FRMT, "truststoreAlgorithmDEF", truststoreAlgorithmDEF));
		stringBuilder.append(String.format(FRMT, "truststoreAlgorithm", keyStoreDefaultType));

		TrustManager[] trustManagers = null;
		InputStream inputStream = null;
		try {
			// get trustStore from file
			File fileTruststore = new File(truststorePath);
			inputStream = new FileInputStream(fileTruststore);
			KeyStore trustStore = KeyStore.getInstance(keyStoreDefaultType);
			trustStore.load(inputStream, truststoreSecret.toCharArray());

			// start TMF to get trustManagers
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(truststoreAlgorithmDEF);
			trustManagerFactory.init(trustStore);
			trustManagers = trustManagerFactory.getTrustManagers();
		}
		catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
			System.out.println(ERROR + ex.getMessage());
		}
		finally {
			try { if ( inputStream != null ) { inputStream.close(); } }
			catch (IOException ex) { System.out.println(ERROR + ex.getMessage()); }
		}
		System.out.println(stringBuilder);
		return trustManagers;
	}

	public static SSLContext getSSLContext(String[] pathPassArray) {

		// C:/Program Files/Java/jdk-17.0.1/lib/security
		SSLContext sslContext = null;
		String keystorePath = pathPassArray[0];
		String keystoreSecret = pathPassArray[1];
		String truststorePath = pathPassArray[2];
		String truststoreSecret = pathPassArray[3];
		String sslCtx_instance = SSLCONTEXT_INSTANCES[0];
		try {
			// inititalize keyManagers, trustManagers, & secureRandom
			KeyManager[] keyManagers =
				getKeyManagers(keystorePath, keystoreSecret); // keystorePath, keystoreSecret
			TrustManager[] trustManagers =
				getTrustManagers(truststorePath, truststoreSecret); // truststorePath, truststoreSecret
			SecureRandom secureRandom = SecureRandom.getInstanceStrong();

			// get sslContext from keyManagers, trustManagers, & secureRandom
			sslContext = SSLContext.getInstance(sslCtx_instance);
			sslContext.init(keyManagers, trustManagers, secureRandom);
		}
		catch (NoSuchAlgorithmException | KeyManagementException ex) {
			System.out.println(ERROR + ex.getMessage());
		}
		return sslContext;
	}

	public static SSLContext getSSLContextApache(String[] pathPassArray) {

		SSLContext sslContext = null;
		String keystorePath = pathPassArray[0];
		String keystoreSecret = pathPassArray[1];
		String truststorePath = pathPassArray[2];
		String truststoreSecret = pathPassArray[3];
		String sslCtx_instance = SSLCONTEXT_INSTANCES[0];
		try {
			URL urlKey = new URL(keystorePath);
			URL urlTrust = new URL(truststorePath);
			char[] charArrayKey = keystoreSecret.toCharArray();
			char[] charArrayTrust = truststoreSecret.toCharArray();
			sslContext = SSLContextBuilder.create()
				.loadTrustMaterial(urlKey, charArrayKey)
				.loadTrustMaterial(urlTrust, charArrayTrust)
				.setProtocol(sslCtx_instance)
				.build();
		}
		catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | CertificateException |
		       IOException ex) {
			System.out.println(ERROR + ex.getMessage());
		}
		return sslContext;
	}

	public static X509Certificate getCertificate(String filenameCert) {

		X509Certificate x509Certificate = null;
		String CERT_TYPE = "X.509";
		try (InputStream inputStream = new FileInputStream(filenameCert)){
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
			x509Certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
		}
		catch (CertificateException | IOException ex) {
			System.out.println(ERROR + ex.getMessage());
		}
		return x509Certificate;
	}

	public static List<X509Certificate> getCertsTruststore(String truststorePath, String truststoreSecret) {

		List<X509Certificate> certificates = null;
		try (InputStream inputStream = new FileInputStream(truststorePath);){
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(inputStream, truststoreSecret.toCharArray());

			PKIXParameters pkixParameters = new PKIXParameters(keyStore);
			Set<TrustAnchor> trustAnchors = pkixParameters.getTrustAnchors();
			certificates = trustAnchors.stream()
				.map(TrustAnchor::getTrustedCert).toList();
		}
		catch (CertificateException | KeyStoreException | InvalidAlgorithmParameterException | IOException |
		       NoSuchAlgorithmException ex) {
			System.out.println(ERROR + ex.getMessage());
		}
		return certificates;
	}
}
