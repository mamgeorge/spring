package com.basics.testmore.util;

import org.junit.jupiter.api.Test;

import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// http://www.atlasoftheuniverse.com/stars.html
// J4: @Test (expected = IOException.class), J5: uses lambda
public class UtilityMainTests {
	//
	public static final String ASSERT_MSG = "ASSERT_MSG";
	public static final String PATHFILE_LOCAL = "src/test/resources/";

	@Test void showSys( ) {
		//
		String txtLines = UtilityMain.showSys();
		System.out.println(PAR + txtLines.replaceAll(",", ",\n\t"));
		assertTrue(txtLines.split(",").length > 20);
	}

	@Test void showTime( ) {
		//
		String txtLine = UtilityMain.showTime();
		System.out.println(PAR + txtLine);
		assertTrue(txtLine.length() > 1);
	}

	// files -n- stuff
	@Test void getFileLines( ) {
		//
		String fileName = "C:/workspace/greetings.txt";
		String txtLines = UtilityMain.getFileLines(fileName, "");
		System.out.println(PAR + txtLines.substring(0, 7));
		assertTrue(txtLines.contains("Autumn"));
	}

	@Test void getFileLocal( ) {
		//
		String txtLines = UtilityMain.getFileLocal(PATHFILE_LOCAL + "Genesis_01.txt", "");
		System.out.println(txtLines.substring(0, 10));
		assertTrue(txtLines.startsWith("Genesis"));
	}

	// url
	@Test void urlGet( ) {
		//
		String link = "http://www.google.com";
		//
		String txtLines = UtilityMain.urlGet(link);
		System.out.println(PAR + txtLines.substring(0, 60));
		assertTrue(txtLines.contains("<!doctype html>"));
	}

	@Test void urlPost( ) {
		//
		String link = "https://httpbin.org/post";
		String postParms = "name=Martin&occupation=programmer";
		//
		String txtLines = UtilityMain.urlPost(link, postParms);
		System.out.println(txtLines.replaceAll(",", ",\n\t")); //.substring(0, 60));
		assertTrue(txtLines.contains("Content-Type"));
	}

	@Test void urlPostFile( ) {
		//
		String link = "https://httpbin.org/post";
		String postParms = "name=Martin&occupation=programmer";
		String pathTxt = PATHFILE_LOCAL + "booksCatalog.json";
		String pathBin = PATHFILE_LOCAL + "hal9000.wav";
		//
		String txtLines = UtilityMain.urlPostFile(link, postParms, pathTxt, pathBin);
		System.out.println(txtLines);
		assertTrue(txtLines.contains("200"));
	}

	// conversions
	@Test void getXmlNode( ) {
		//
		String xml = "<a><b id = 'aleph' ><c><d>alpha</d><d>beta</d></c></b><b id = 'beth' ></b></a>";
		String xpath = "a/b/c/d";
		//
		String txtLine = "";
		txtLine += PAR + UtilityMain.getXmlNode(xml, xpath);
		txtLine += PAR + UtilityMain.getXmlNode(xml, "a/b/c/d[2]");
		txtLine += PAR + UtilityMain.getXmlNode(xml, "a/b[2]/@id");
		//
		System.out.println(txtLine);
		assertTrue(txtLine.length() > 1);
	}

	@Test void convertXml2Json( ) {
		//
		String xml = "<a><b id = 'aleph' ><c><d>alpha</d><d>beta</d></c></b><b id = 'beth' ></b></a>";
		String txtLines = UtilityMain.convertXml2Json(xml);
		System.out.println(PAR + txtLines.replaceAll("\n", "").replaceAll("\t", "").replaceAll("  ", ""));
		assertTrue(txtLines.length() > 1);
	}

	@Test void convertJson2Xml( ) {
		//
		String json = "{ a: { b: [ { c: { d: [ alpha, beta ] }, id: aleph }, { id: beth } ] } }";
		String txtLines = UtilityMain.convertJson2Xml(json);
		System.out.println(PAR + txtLines.replaceAll("\n", ""));
		assertTrue(txtLines.length() > 1);
	}

	@Test void formatXml( ) {
		//
		String xml = "<a><b><c><d>alpha</d><d>beta</d></c><id>aleph</id></b><b><id>beth</id></b></a>";
		String txtLines = UtilityMain.formatXml(xml);
		System.out.println(PAR + txtLines.substring(0, 20));
		assertTrue(txtLines.length() > 1);
	}

	@Test void parseYaml2JsonNode( ) {
		//
		String yamlFileName = PATHFILE_LOCAL + "application.yml";
		String applicationNode = "datasource.platform";
		//
		String txtLine = UtilityMain.parseYaml2JsonNode(yamlFileName, applicationNode);
		System.out.println(PAR + txtLine);
		assertEquals("h2", txtLine);
	}

	@Test void parseJsonList2List( ) {
		//
		String jsonArr = "[ {\"a\":\"1\"} , {\"b\":\"2\"}, {\"c\":\"3\"} ]";
		//
		String txtLines = UtilityMain.parseJsonList2List(jsonArr, 1);
		System.out.println(PAR + txtLines);
		assertTrue(txtLines.length() > 1);
	}
}
