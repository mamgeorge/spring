package com.basics.securing.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Map;

import static com.basics.securing.utils.UtilityMain.EOL;
import static com.basics.securing.utils.UtilityMain.exposeObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilityMainTest {

	public static final String PATH_RESOURCES_TEST = "src/test/resources/";
	public static final String PATH_RESOURCES_MAIN = "src/main/resources/";

	@BeforeAll static void init( ) {

		System.out.println("enable --illegal-access" + EOL);
		// --add-opens java.base/java.lang=ALL-UNNAMED
		// --illegal-access=permit
	}

	@Test void showSys( ) {

		String txtLines = UtilityMain.showSys();
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void getEnvironment( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment environment = UtilityMain.getEnvironment();

		stringBuilder.append(EOL + "1 environment: " + EOL);
		stringBuilder.append("\t" + exposeObject(environment));

		stringBuilder.append(EOL + "2 getProperty" + EOL);
		stringBuilder.append(String.format(FRMT, "any.prop.path",
			environment.getProperty("any.prop.path")));
		stringBuilder.append(String.format(FRMT, "spring.application.id",
			environment.getProperty("spring.application.id")));
		stringBuilder.append(String.format(FRMT, "server.servlet.context-path",
			environment.getProperty("server.servlet.context-path")));

		System.out.println(stringBuilder);
		assertNotNull(environment);
	}

	@Test void getEnvironment_profiles( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment environment = UtilityMain.getEnvironment();

		String[] activeProfiles = environment.getActiveProfiles();
		stringBuilder.append("1 activeProfiles: " + activeProfiles.length + EOL);
		Arrays.stream(activeProfiles)
			.sorted()
			.forEach(activeProfile -> { stringBuilder.append(String.format(FRMT, activeProfile, "")); });
		stringBuilder.append(EOL);

		String[] defaultProfiles = environment.getDefaultProfiles();
		stringBuilder.append("2 defaultProfiles: " + defaultProfiles.length + EOL);
		Arrays.stream(defaultProfiles)
			.sorted()
			.forEach(defaultProfile -> { stringBuilder.append(String.format(FRMT, defaultProfile, "")); });

		System.out.println(stringBuilder);
		assertNotNull(environment);
	}

	@Test void getEnvironment_systemProps( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment environment = UtilityMain.getEnvironment();

		Map<String, Object> mapSystemEnv = environment.getSystemEnvironment();
		stringBuilder.append("1 mapSystemEnv: " + mapSystemEnv.size() + EOL);
		mapSystemEnv.forEach(
			(key, val) -> { stringBuilder.append(String.format(FRMT, key, val.toString())); });
		stringBuilder.append(EOL);

		Map<String, Object> mapSystemProps = environment.getSystemProperties();
		stringBuilder.append("2 mapSystemProps: " + mapSystemProps.size() + EOL);
		mapSystemProps.forEach(
			(key, val) -> { stringBuilder.append(String.format(FRMT, key, val.toString())); });

		System.out.println(stringBuilder);
		assertNotNull(environment);
	}
}
