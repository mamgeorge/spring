package com.basics.testmore.util;

import com.basics.testmore.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilityMainTest {
	//
	public static final String ASSERT_MSG = "ASSERT_MSG";
	public static final String PATHFILE_LOCAL = "src/test/resources/";
	@Mock List<String> listMock;

	@BeforeEach public void setup() {
		// if we don't call below, we will get NullPointerException
		MockitoAnnotations.openMocks(this);
	}

	// J4: @Test (expected = IOException.class), J5: uses lambda
	@Test public void showSys() {
		//
		String txtLines = UtilityMain.showSys();
		System.out.println(PAR + txtLines.replaceAll(",", ",\n\t"));
		assertTrue(txtLines.split(",").length > 20);
	}

	@Test public void showTime() {
		//
		String txtLine = UtilityMain.showTime();
		System.out.println(PAR + txtLine);
		assertTrue(txtLine.length() > 1);
	}

	// uses: mock, when.thenReturn()
	@Test public void showTimeMock1() {
		//
		Date dateMock = mock(Date.class);
		when(dateMock.toString()).thenReturn("2021/02/20").thenReturn("12:34:56 AM");
		String txtLine = dateMock + " " + dateMock;
		System.out.println(PAR + txtLine);
		assertTrue(txtLine.length() > 10);
	}

	// uses: mock, when, anyLong()
	@Test public void showTimeMock2() {
		//
		Date dateMock = mock(Date.class);
		dateMock.setTime(anyLong());
		when(dateMock.toString()).thenReturn("2021/02/20 12:34:56 AM");
		System.out.println(PAR + dateMock);
		assertTrue(dateMock.toString().length() > 20);
	}

	// uses: mock, when, doReturn
	@Test public void showTimeMock3() {
		//
		Date dateMock = mock(Date.class);
		doReturn(1000L).when(dateMock).getTime();
		//
		System.out.println(PAR + dateMock.toString() + " / " + dateMock.getTime()); // Mock for Date, hashCode: 1947064457
		assertEquals(1000L, dateMock.getTime());
	}

	@Test public void showTimeMock4() {
		//
		when(listMock.get(0)).thenReturn("MGeorge");
		assertEquals("MGeorge", listMock.get(0));
	}

	// http://www.atlasoftheuniverse.com/stars.html

	@Test public void getFileLines() {
		//
		String fileName = "C:/workspace/greetings.txt";
		String txtLines = UtilityMain.getFileLines(fileName, "");
		System.out.println(PAR + txtLines.substring(0, 7));
		assertTrue(txtLines.contains("Autumn"));
	}

	@Test public void getFileLocal() {
		//
		String txtLines = UtilityMain.getFileLocal(PATHFILE_LOCAL + "Genesis_01.txt", "");
		System.out.println(txtLines.substring(0, 10));
		assertTrue(txtLines.startsWith("Genesis"));
	}

	@Test public void getField() {
		//
		City city = new City("Columbus", 730000);
		String txtObject = UtilityMain.getField(city, "name");
		System.out.println("getField: " + txtObject);
		assertEquals("Columbus", txtObject);
	}

	@Test public void urlGet() {
		//
		String link = "http://www.google.com";
		//
		String txtLines = UtilityMain.urlGet(link);
		System.out.println(PAR + txtLines.substring(0, 60));
		assertTrue(txtLines.contains("<!doctype html>"));
	}

	@Test public void urlPost() {
		//
		String link = "https://httpbin.org/post";
		String postParms = "name=Martin&occupation=programmer";
		//
		String txtLines = UtilityMain.urlPost(link, postParms);
		System.out.println(txtLines.replaceAll(",", ",\n\t")); //.substring(0, 60));
		assertTrue(txtLines.contains("Content-Type"));
	}

	@Test public void urlPostFile() {
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

	@Test public void getXmlNode() {
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
		assertTrue(txtLines.length() > 1);
	}

	@Test public void parseYaml2JsonNode() {
		//
		String yamlFileName = PATHFILE_LOCAL + "application.yml";
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
}
