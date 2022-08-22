package com.basics.testmore.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilityExtraTest {

	@Test
	void main( ) {
		UtilityExtra utilityExtra = new UtilityExtra();
		UtilityExtra.main(new String[]{ "" });
		assertNotNull(utilityExtra);
	}
}