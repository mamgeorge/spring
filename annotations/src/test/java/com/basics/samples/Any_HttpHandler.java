package com.basics.samples;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

public class Any_HttpHandler implements HttpHandler {

	// https://dzone.com/articles/simple-http-server-in-java
	private static final Logger LOGGER = Logger.getLogger(Any_HttpHandler.class.getName());

	private static final String HTML_TOP = "<html><style>body {font-family: verdana;}</style><body><center>";
	private static final String HTML_BTM = "</center></body></html>";

	@Override public void handle(HttpExchange httpExchange) throws IOException {
		//
		String parm = "";
		String method = httpExchange.getRequestMethod();
		if (GET.toString().equals(method)) {
			parm = handleGets(httpExchange);
		}
		if (POST.toString().equals(method)) {
			parm = handlePost(httpExchange);
		}
		handleResponse(httpExchange, parm);
	}

	private String handleGets(HttpExchange httpExchange) {
		//
		String txtLines = "";
		String uri = httpExchange.getRequestURI().toString();
		if (uri.contains("?")) {
			txtLines = uri.split("\\?")[1];
		}
		if (uri.contains("=")) {
			txtLines = uri.split("=")[1];
		}
		//
		InputStream inputStream = httpExchange.getRequestBody();
		try {txtLines += new String(inputStream.readAllBytes(), UTF_8);}
		catch (IOException ex) { LOGGER.severe(ex.getMessage()); }
		//
		return txtLines;
	}

	private String handlePost(HttpExchange httpExchange) {
		//
		String txtLines = "";
		InputStream inputStream = httpExchange.getRequestBody();
		try {txtLines = new String(inputStream.readAllBytes(), UTF_8);}
		catch (IOException ex) { LOGGER.severe(ex.getMessage()); }
		return txtLines;
	}

	private void handleResponse(HttpExchange httpExchange, String parms) {
		//
		OutputStream outputStream = httpExchange.getResponseBody();
		String html = HTML_TOP;
		html += "<h5>" + Instant.now() + "</h5>" + "<hr >" + parms;
		html += HTML_BTM;
		// StringEscapeUtils.escapeHtml4(...)
		try {
			httpExchange.sendResponseHeaders(OK.value(), html.length());
			outputStream.write(html.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException ex) {
			LOGGER.severe(ex.getMessage());
		}
	}
}
