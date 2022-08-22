package com.basics.testmore.model;

import com.basics.testmore.util.UtilityMain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CountryRepositoryTest {

	@Test public void testSettersGetters( ) {
		//
		Country country = new Country();
		System.out.println(UtilityMain.exposeObject(country));

		assertNotNull(country);
	}
}
