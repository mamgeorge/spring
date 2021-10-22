package com.example.sb_webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplicationWebFlux {

	public static void main(String[] args) {
		//
		ConfigurableApplicationContext context = SpringApplication.run(SpringApplicationWebFlux.class, args);
		WebClientGreeting webClientGreeting = context.getBean(WebClientGreeting.class);
		//
		System.out.println(">> message = " + webClientGreeting.getMessage().block());
	}
}
