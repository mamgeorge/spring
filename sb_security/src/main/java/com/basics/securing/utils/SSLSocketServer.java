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

	public static final String SSLCONTEXT_INSTANCE = SSLCONTEXT_INSTANCES[1];
	public static final int PORT = 80;
	public static final int BYTE_BLOCK = 2048;

	public static void main(String[] args) {

		System.out.println("SSLSocketServer.main()");
		startServer(PORT);
	}

	public static void startServer(int port ) {

		ServerSocketFactory SSFactory = SSLServerSocketFactory.getDefault();
		try {
			// sslServerSocket (listener) checks server_truststore to confirm self_signed client_cert is there
			SSLServerSocket sslServerSocket = (SSLServerSocket) SSFactory.createServerSocket(port);
			sslServerSocket.setNeedClientAuth(true); // requires client to share client_cert with server
			sslServerSocket.setEnabledCipherSuites(new String[]{ ENABLEDCIPHER_SUITES });
			sslServerSocket.setEnabledProtocols(new String[]{ SSLCONTEXT_INSTANCE });
			System.out.println("\nSERVER LISTENING FOR MESSAGES!\n");
			try {
				Socket socket = sslServerSocket.accept();
				InputStream inputStream = new BufferedInputStream(socket.getInputStream());
				byte[] bytes = new byte[BYTE_BLOCK];
				System.out.println("inputStream: " + inputStream);
				int lenBytes;
				lenBytes = inputStream.read(bytes);

				System.out.println("writing...");
				String message = new String(bytes, 0, lenBytes);
				OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
				System.out.printf("SERVER RECEIVED %d bytes: [%s]%n", lenBytes, message);
				String serverResponse = "[" + message + "] processed by SERVER";
				outputStream.write(serverResponse.getBytes(), 0, serverResponse.getBytes().length);
				outputStream.flush();
			}
			catch (IOException ex) { System.out.println("ERROR Socket: " + ex.getMessage()); }
		}
		catch (IOException ex) { System.out.println("ERROR SSLServerSocket: " + ex.getMessage()); }
	}
}
