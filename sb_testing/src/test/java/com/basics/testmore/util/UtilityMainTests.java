package com.basics.testmore.util;

import org.junit.jupiter.api.Test;

import static com.basics.testmore.util.UtilityMain.PAR;

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
}
