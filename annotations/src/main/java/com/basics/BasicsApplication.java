package com.basics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

// C:\workspace\github\spring_annotations\src\main\java\com\basics\BasicsApplication.java
// $ mvn spring-boot:run
// locate to localhost:8080/basics 

/*
	@SpringBootApplication is a convenience annotation that adds all this:
	
	@Configuration				: tags class as a source of bean definitions for application context
	@EnableAutoConfiguration	: adds beans based on classpath settings, other beans, etc.
	@EnableWebMvc				: setting up a DispatcherServlet if spring-webmvc is on classpath
	@ComponentScan				: tells Spring to look for components, configurations, services in package
*/
@SpringBootApplication
public class BasicsApplication implements CommandLineRunner {
	//
	// CLR (CommandLineRunner) not normally implemented	
	// CLR, Autowired, Override, only added to show how env vars can be accessed
	@Autowired private Environment environment;

	@Override public void run(String... args) throws Exception {

		//
		String appName = environment.getProperty("app.name");
		String urlPort = environment.getProperty("server.servlet.port");
		String urlPath = environment.getProperty("server.servlet.context-path");
		String txtLine = appName + " at http://localhost:" + urlPort + urlPath;
		System.out.println("\n" + "Started! Running: " + txtLine);
	}

	//
	public static void main(String[] args) {
		//
		System.out.println("HELLO from BasicsApplication"); //  \u001B[31m
		//
		// SpringApplication.run( BasicsApplication.class, args );
		SpringApplication springApplication = new SpringApplication(BasicsApplication.class);
		springApplication.setBannerMode(Banner.Mode.CONSOLE);
		springApplication.run(args);
	}
}
