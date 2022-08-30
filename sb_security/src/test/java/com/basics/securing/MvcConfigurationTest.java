package com.basics.securing;

// import org.junit.Test; // JUnit 4
import org.junit.jupiter.api.Test; // JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;

public class MvcConfigurationTest {

	public static final Logger LOGGER = Logger.getLogger( MvcConfigurationTest.class.getName( ) );

	// test_addViewControllers
	@Test public void testViewControllers( ) {
		//
		String txtLine = "#### DONE ####";
		System.out.println( txtLine  + " / " + txtLine.length() );
		LOGGER.info( txtLine );
		assertEquals( 14 , txtLine.length() );
	}
}

