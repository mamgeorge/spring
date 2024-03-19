package com.basics.testmore.util;

import org.junit.jupiter.api.Test;

import static com.basics.testmore.util.UtilityMain.PAR;
import static com.basics.testmore.util.UtilityMainTests.PATHFILE_LOCAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// http://www.atlasoftheuniverse.com/stars.html
// J4: @Test (expected = IOException.class), J5: uses lambda
class UtilityConversionTests {

	@Test void getXmlNode( ) {
		//
		String xml = "<a><b id = 'aleph' ><c><d>alpha</d><d>beta</d></c></b><b id = 'beth' ></b></a>";
		String xpath = "a/b/c/d";
		//
		String txtLine = "";
		txtLine += PAR + UtilityConversion.getXmlNode(xml, xpath);
		txtLine += PAR + UtilityConversion.getXmlNode(xml, "a/b/c/d[2]");
		txtLine += PAR + UtilityConversion.getXmlNode(xml, "a/b[2]/@id");
		//
		System.out.println(txtLine);
		assertTrue(txtLine.length() > 1);
	}

	@Test void formatXml( ) {
		//
		String xml = "<a><b><c><d>alpha</d><d>beta</d></c><id>aleph</id></b><b><id>beth</id></b></a>";
		String txtLines = UtilityConversion.formatXml(xml);
		System.out.println(PAR + txtLines.substring(0, 20));
		assertTrue(txtLines.length() > 1);
	}

	@Test void parseYaml2JsonNode( ) {
		//
		String yamlFileName = PATHFILE_LOCAL + "application.yml";
		String applicationNode = "datasource.platform";
		//
		String txtLine = UtilityConversion.parseYaml2JsonNode(yamlFileName, applicationNode);
		System.out.println(PAR + txtLine);
		assertEquals("h2", txtLine);
	}

	@Test void parseJsonList2List( ) {
		//
		String jsonArr = "[ {\"a\":\"1\"} , {\"b\":\"2\"}, {\"c\":\"3\"} ]";
		//
		String txtLines = UtilityConversion.parseJsonList2List(jsonArr, 1);
		System.out.println(PAR + txtLines);
		assertTrue(txtLines.length() > 1);
	}
}
