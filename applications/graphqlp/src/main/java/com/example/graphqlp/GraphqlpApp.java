package com.example.graphqlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

@SpringBootApplication
public class GraphqlpApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext caContext = SpringApplication.run(GraphqlpApp.class, args);
		startupCheck(caContext);
	}

	private static void startupCheck(ConfigurableApplicationContext caContext) {

		ConfigurableEnvironment environment = caContext.getEnvironment();
		String serverPort = environment.getProperty("local.server.port");
		String activeProfiles = Arrays.toString(environment.getActiveProfiles());
		String config = environment.getProperty("username");

		String FORM = "%n\t serverPort: %s %n\t activeProfiles: %s %n\t config: %s%n%n";
		System.out.printf(FORM, serverPort, activeProfiles, config);
	}
}
