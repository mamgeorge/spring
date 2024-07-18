package com.example.ntier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT )
class NtierApplicationTests {

	@Autowired ApplicationContext appContext; // springApplication.run(strings);
	// @Autowired Environment environment; // ConfigurableEnvironment

	@Test void contextLoads( ) {

		Environment env = appContext.getEnvironment();
		StringBuilder sb = new StringBuilder();
		String FRMT = "\t%-15s %s%n";

		sb.append(format(FRMT, "port", env.getProperty("server.port")));
		sb.append(format(FRMT, "activeProfiles", Arrays.toString(env.getActiveProfiles())));
		sb.append(format(FRMT, "defaultProfiles", Arrays.toString(env.getDefaultProfiles())));

		System.out.println(sb);
		assertThat(sb).isNotEmpty();
	}
}
