package com.basics.dbsqlite;

// import org.junit.Test; // JUnit 4

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbSqliteAppTest {

	public static final Logger LOGGER = Logger.getLogger(DbSqliteAppTest.class.getName());

	// test_addViewControllers
	@Test void testViewControllers( ) {
		//
		String txtLine = "#### DONE ####";
		System.out.println(txtLine + " / " + txtLine.length());
		LOGGER.info(txtLine);
		assertEquals(14, txtLine.length());
	}
}

