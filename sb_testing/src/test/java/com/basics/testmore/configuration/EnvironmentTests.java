package com.basics.testmore.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnvironmentTests {

	public static final String EOL = "\n";

	@Test void context( ) {

		String txtLines = "";
		AnnotationConfigApplicationContext ACAC = new AnnotationConfigApplicationContext();
		ConfigurableEnvironment configurableEnvironment = ACAC.getEnvironment();

		MutablePropertySources MPS = configurableEnvironment.getPropertySources();
		Map<String, Object> map = new HashMap<>();
		map.put("oneprop", 343);
		map.put("twoprop", "737");
		MPS.addFirst(new MapPropertySource("anyupdate", map));

		txtLines += configurableEnvironment.getProperty("oneprop") + EOL;
		txtLines += configurableEnvironment.getProperty("twoprop") + EOL;
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

}
