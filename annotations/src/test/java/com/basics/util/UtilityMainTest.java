// cd c:\workspace\github\spring_annotations

package com.basics.util;

import com.basics.samples.Any_HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

import static com.basics.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilityMainTest {

	private static final Logger LOGGER = Logger.getLogger(UtilityMainTest.class.getName());
	private static final String PATH_LOCAL = "src/test/java/resources/";
	private static final String HOST_EXT = "https://httpbin.org/";
	private static final String TXT_SAMPLE = "Genesis_01.txt";

	public static void main(String[] args) {
		//
		sample_HttpCient(HOST_EXT+"get?id-1234");
		// sample_HttpServer(3000);
	}

	// J4: @Test (expected = IOException.class), J5: uses lambda
	@BeforeAll public void setUp() {
	}

	@Test public void showSys() {
		//
		String txtLines = UtilityMain.showSys();
		System.out.println(PAR + txtLines.substring(0, 10));
		assertTrue(txtLines.length() > 10);
	}

	@Test public void showTime() {
		//
		String txtLine = UtilityMain.showTime();
		System.out.println(PAR + txtLine);
		assertTrue(txtLine.length() > 10);
	}

	@Test public void getFileLines() {
		//
		String fileName = "/workspace/greetings.txt";
		String txtLines = UtilityMain.getFileLines(fileName, "");
		//System.out.println( "[#### " + PAR + txtLines + " ####]" );
		System.out.println(PAR + txtLines);
		assertTrue(txtLines.contains("Autumn"));
	}

	@Test public void getFileLocal() {
		//
		String txtLines = UtilityMain.getFileLocal(PATH_LOCAL + TXT_SAMPLE, "");
		System.out.println("getFileLocal: " + txtLines);
		assertTrue(txtLines.startsWith("Genesis"));
	}

	@Test public void urlGet() {
		//
		String link = "http://www.google.com";
		String txtLines = UtilityMain.urlGet(link);
		System.out.println(PAR + txtLines.substring(0, 60));
		assertTrue(txtLines.length() > 10);
	}

	@Test public void urlPost() {
		//
		String link = "https://httpbin.org/post";
		String postParms = "name=Martin&occupation=programmer";
		//
		String txtLines = UtilityMain.urlPost(link, postParms);
		System.out.println(PAR + txtLines.substring(0, 60));
		assertTrue(txtLines.length() > 10);
	}

	@Test public void urlPostFile() {
		//
		String link = "https://httpbin.org/post";
		String postParms = "name=Martin&occupation=programmer";
		String pathTxt = PATH_LOCAL + "booksCatalog.json";
		String pathBin = PATH_LOCAL + "hal9000.wav";
		//
		String txtLines = UtilityMain.urlPostFile(link, postParms, pathTxt, pathBin);
		System.out.println(PAR + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	@Test public void getXmlNode() {
		//
		String txtLine = "";
		String xml = "<a><b id = 'aleph' ><c><d>alpha</d><d>beta</d></c></b><b id = 'beth' ></b></a>";
		String xpath = "a/b/c/d";
		//
		txtLine += PAR + UtilityMain.getXmlNode(xml, xpath);
		txtLine += PAR + UtilityMain.getXmlNode(xml, "a/b/c/d[2]");
		txtLine += PAR + UtilityMain.getXmlNode(xml, "a/b[2]/@id");
		//
		System.out.println(txtLine);
		assertTrue(txtLine.length() > 10);
	}

	@Test public void convertXml2Json() {
		//
		String xml = "<a><b id = 'aleph' ><c><d>alpha</d><d>beta</d></c></b><b id = 'beth' ></b></a>";
		String txtLines = UtilityMain.convertXml2Json(xml);
		System.out.println(PAR + txtLines.replaceAll("\n", "").replaceAll("\t", "").replaceAll("  ", ""));
		assertTrue(txtLines.length() > 1);
	}

	@Test public void convertJson2Xml() {
		//
		String json = "{ a: { b: [ { c: { d: [ alpha, beta ] }, id: aleph }, { id: beth } ] } }";
		String txtLines = UtilityMain.convertJson2Xml(json);
		System.out.println(PAR + txtLines.replaceAll("\n", ""));
		assertTrue(txtLines.length() > 1);
	}

	@Test public void formatXml() {
		//
		String xml = "<a><b><c><d>alpha</d><d>beta</d></c><id>aleph</id></b><b><id>beth</id></b></a>";
		String txtLines = UtilityMain.formatXml(xml);
		System.out.println(PAR + txtLines.substring(0, 20));
		assertTrue(txtLines.length() > 10);
	}

	@Test public void parseYaml2JsonNode() {
		//
		String yamlFileName = "application.yml";
		String applicationNode = "datasource.platform";
		//
		String txtLine = UtilityMain.parseYaml2JsonNode(yamlFileName, applicationNode);
		System.out.println(PAR + txtLine);
		assertEquals("h2", txtLine);
	}

	@Test public void parseJsonList2List() {
		//
		String jsonArr = "[ {\"a\":\"1\"} , {\"b\":\"2\"}, {\"c\":\"3\"} ]";
		//
		String txtLines = UtilityMain.parseJsonList2List(jsonArr, 1);
		System.out.println(PAR + txtLines);
		assertTrue(txtLines.length() > 1);
	}

	private static void sample_HttpCient(String url) {
		//
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(url))
				.setHeader(USER_AGENT, "Java11Client Bot")
				.build();
		HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
		HttpResponse<String> httpResponse = null;
		try {
			HttpResponse.BodyHandler<String> bodyHandlers = HttpResponse.BodyHandlers.ofString();
			httpResponse = httpClient.send(httpRequest, bodyHandlers);
		} catch (IOException | InterruptedException ex) {System.out.println("ERROR: " + ex.getMessage());}
		//
		System.out.println( "body: "+ httpResponse.body() );
	}

	private static void sample_HttpServer(int PORT) {
		//
		Any_HttpHandler anyHttpHandler = new Any_HttpHandler();
		String HOST = "localhost", CONTEXT = "/";
		int backlog = 0, threads = 10;
		try {
			InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
			HttpServer httpServer = HttpServer.create(inetSocketAddress, backlog);
			ThreadPoolExecutor TPE = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
			//
			httpServer.createContext(CONTEXT, anyHttpHandler);
			httpServer.setExecutor(TPE);
			httpServer.start();
			System.out.println("Server started on port: " + PORT);
		} catch (IOException ex) {
			LOGGER.severe(ex.getMessage());
		}
	}
}
