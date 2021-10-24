package com.example.sb_webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SpringApplicationWebFluxTest {

	private static final String ASSERTION = "ASSERTION";

	@Value("${spring.datasource.maximum-pool-size}") private int connectionPoolSize;

	@Test void contextLoads() {
		//
		System.out.println("connectionPoolSize: " + connectionPoolSize);
		Assert.isTrue(connectionPoolSize == 100, ASSERTION);

	}
}
