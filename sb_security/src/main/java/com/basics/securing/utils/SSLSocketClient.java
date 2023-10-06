package com.basics.securing.utils;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.basics.securing.utils.SSLSocketServer.PORT;
import static com.basics.securing.utils.SSLSocketServer.SSLCONTEXT_INSTANCE;
import static com.basics.securing.utils.SecurityCode.ENABLEDCIPHER_SUITES;

public class SSLSocketClient {

	public static final String HOST = "localhost";
	public static final int PORTED = PORT;

	public static void main(String[] args) {

		System.out.println("SSLSocketClient.main()");
		startClient(HOST, PORTED);
	}

	public static void startClient(String host, int port ) {

		SocketFactory socketFactory = SSLSocketFactory.getDefault();
		try {
			// sslSocket creates TLS connection, and client verifies server_cert is in the client_truststore
			SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(host, port);
			sslSocket.setEnabledCipherSuites(new String[]{ ENABLEDCIPHER_SUITES });
			sslSocket.setEnabledProtocols(new String[]{ SSLCONTEXT_INSTANCE });

			String message = "CLIENT MESSAGE: Hello World!";
			System.out.println("sending message: " + message);
			OutputStream outputStream = new BufferedOutputStream(sslSocket.getOutputStream());
			outputStream.write(message.getBytes());
			outputStream.flush();

			InputStream inputStream = new BufferedInputStream(sslSocket.getInputStream());
			byte[] bytes = new byte[2048];
			int lenBytes = inputStream.read(bytes);
			String serverResponse = new String(bytes, 0, lenBytes);
			System.out.printf("CLIENT RECEIVED %d bytes: %s%n", lenBytes, serverResponse);
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}
}
