package com.basics.securing.utils;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.basics.securing.utils.SecurityCode.ENABLEDCIPHER_SUITES;
import static com.basics.securing.utils.SecurityCode.SSLCONTEXT_INSTANCES;

public class SSLSocketServer {

	public static final int PORT = 80;

	public static void main(String[] args) {

		ServerSocketFactory SSFactory = SSLServerSocketFactory.getDefault();
		try {
			// sslServerSocket (listener) checks server_truststore to confirm self_signed client_cert is there
			SSLServerSocket sslServerSocket = (SSLServerSocket) SSFactory.createServerSocket(PORT);
			sslServerSocket.setNeedClientAuth(true); // requires client to share client_cert with server
			sslServerSocket.setEnabledCipherSuites(new String[] { ENABLEDCIPHER_SUITES });
			sslServerSocket.setEnabledProtocols(new String[] { SSLCONTEXT_INSTANCES[1] });
			System.out.println("listening for messages...");
			try {
				Socket socket = sslServerSocket.accept();
				InputStream inputStream = new BufferedInputStream(socket.getInputStream());
				byte[] bytes = new byte[2048];
				int lenBytes = inputStream.read(bytes);

				String message = new String(bytes, 0, lenBytes);
				OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
				System.out.printf("server received %d bytes: %s%n", lenBytes, message);
				String response = message + " processed by server";
				outputStream.write(response.getBytes(), 0, response.getBytes().length);
				outputStream.flush();
			}
			catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage());}
	}
}
