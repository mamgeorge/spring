package com.example.embedded;

import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmbeddedApplicationTests {

	public static final Logger LOGGER = Logger.getLogger(EmbeddedApplicationTests.class.getName());
	private static final String ASSERTION = "ASSERTION";

	@Test void contextLoads() {
		LOGGER.info(ASSERTION);
	}
}
