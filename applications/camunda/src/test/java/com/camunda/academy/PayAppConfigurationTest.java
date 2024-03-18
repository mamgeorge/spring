package com.camunda.academy;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static com.camunda.academy.PayAppConfiguration.EOL;
import static com.camunda.academy.PayAppConfiguration.RESOURCES_FILE;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PayAppConfigurationTest {

	@Test void toString_test( ) {

		PayAppConfiguration payAppConf = new PayAppConfiguration();
		String txtLines = payAppConf.toString();
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void getResourceProps_test( ) {

		PayAppConfiguration payAppConf = new PayAppConfiguration();
		Properties properties = payAppConf.getResourceProps(RESOURCES_FILE);

		String txtLines = properties.toString();
		System.out.println(txtLines + EOL);

		StringBuilder stringBuilder = new StringBuilder();
		properties.forEach((key, val) -> {
			String txtLine = String.format("\t%-30s %s\n", key, val);
			stringBuilder.append(txtLine);
		});
		System.out.println(stringBuilder);

		assertTrue(true);
	}
}
