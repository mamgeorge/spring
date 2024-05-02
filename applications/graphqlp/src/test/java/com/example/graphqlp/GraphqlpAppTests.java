package com.example.graphqlp;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

// @Disabled( "integration only" )
class GraphqlpAppTests {

	@Test void environment_test( ) {

		ConfigurableEnvironment environment = new StandardEnvironment();
		String serverPort = environment.getProperty("local.server.port");
		String activeProfiles = Arrays.toString(environment.getActiveProfiles());
		String config = environment.getProperty("username");

		String FORM = "%n\t serverPort: %s %n\t activeProfiles: %s %n\t config: %s%n%n";
		System.out.printf(FORM, serverPort, activeProfiles, config);
		assertTrue(true);
	}

	@Test void context_test( ) {

		GenericApplicationContext context = new AnnotationConfigApplicationContext();
		ConfigurableEnvironment environment = context.getEnvironment();
		String serverPort = environment.getProperty("local.server.port");
		String activeProfiles = Arrays.toString(environment.getActiveProfiles());
		String config = environment.getProperty("username");

		String FORM = "%n\t serverPort: %s %n\t activeProfiles: %s %n\t config: %s%n%n";
		System.out.printf(FORM, serverPort, activeProfiles, config);
		assertTrue(true);
	}
}

