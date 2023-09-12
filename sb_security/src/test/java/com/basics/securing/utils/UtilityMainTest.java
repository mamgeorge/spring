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

	public static final String PATHRESOURCE_TEST = "src/test/resources/";
	public static final String PATHRESOURCE_MAIN = "src/main/resources/";

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

		StringBuilder sb = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment env = UtilityMain.getEnvironment();

		sb.append(EOL + "1 env: " + EOL);
		sb.append("\t" + exposeObject(env));

		sb.append(EOL + "2 getProperty" + EOL);
		sb.append(String.format(FRMT, "any.prop.path", env.getProperty("any.prop.path")));
		sb.append(String.format(FRMT, "spring.application.id", env.getProperty("spring.application.id")));
		sb.append(String.format(FRMT, "server.servlet.context-path", env.getProperty("server.servlet.context-path")));

		System.out.println(sb);
		assertNotNull(env);
	}

	@Test void getEnvironment_profiles( ) {

		StringBuilder sb = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment env = UtilityMain.getEnvironment();

		String[] activeProfiles = env.getActiveProfiles();
		sb.append("1 activeProfiles: " + activeProfiles.length + EOL);
		Arrays.stream(activeProfiles).sorted().forEach(activeProfile
			-> sb.append(String.format(FRMT, activeProfile, "")));
		sb.append(EOL);

		String[] defaultProfiles = env.getDefaultProfiles();
		sb.append("2 defaultProfiles: " + defaultProfiles.length + EOL);
		Arrays.stream(defaultProfiles).sorted().forEach(defaultProfile
			-> sb.append(String.format(FRMT, defaultProfile, "")));

		System.out.println(sb);
		assertNotNull(env);
	}

	@Test void getEnvironment_systemProps( ) {

		StringBuilder sb = new StringBuilder();
		String FRMT = "\t%-20s / %s\n";
		ConfigurableEnvironment env = UtilityMain.getEnvironment();

		Map<String, Object> mapSystemEnv = env.getSystemEnvironment();
		sb.append("1 mapSystemEnv: " + mapSystemEnv.size() + EOL);
		mapSystemEnv.forEach((key, val) -> sb.append(String.format(FRMT, key, val.toString())));
		sb.append(EOL);

		Map<String, Object> mapSystemProps = env.getSystemProperties();
		sb.append("2 mapSystemProps: " + mapSystemProps.size() + EOL);
		mapSystemProps.forEach((key, val) -> sb.append(String.format(FRMT, key, val.toString())));

		System.out.println(sb);
		assertNotNull(env);
	}
}
