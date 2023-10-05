package com.basics.securing.utils;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.basics.securing.utils.SecurityCode.ENABLEDCIPHER_SUITES;
import static com.basics.securing.utils.SecurityCode.SSLCONTEXT_INSTANCES;

public class SSLSocketClient {

	public static final String HOST = "localhost";
	public static final int PORT = 80;

	public static void main(String[] args) {

		SocketFactory socketFactory = SSLSocketFactory.getDefault();
		try  {
			// sslSocket creates TLS connection, and client verifies server_cert is in the client_truststore
			SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(HOST, PORT);
			sslSocket.setEnabledCipherSuites(new String[] { ENABLEDCIPHER_SUITES });
			sslSocket.setEnabledProtocols(new String[] { SSLCONTEXT_INSTANCES[1] });

			String message = "Hello World Message";
			System.out.println("sending message: " + message);
			OutputStream outputStream = new BufferedOutputStream(sslSocket.getOutputStream());
			outputStream.write(message.getBytes());
			outputStream.flush();

			InputStream inputStream = new BufferedInputStream(sslSocket.getInputStream());
			byte[] bytes = new byte[2048];
			int lenBytes = inputStream.read(bytes);
			System.out.printf("client received %d bytes: %s%n", lenBytes, new String(bytes, 0, lenBytes));
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}
}
