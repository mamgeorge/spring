package com.example.sb_webflux;

import com.example.sb_webflux.configuration.WebClientCity;
import com.example.sb_webflux.configuration.WebClientGreeting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplicationWebFlux {

	static boolean boolGreet = false;
	static String ID_START = "10";

	public static void main(String[] args) {
		//
		ConfigurableApplicationContext context = SpringApplication.run(SpringApplicationWebFlux.class, args);
		if (boolGreet) {
			WebClientGreeting webClientGreeting = context.getBean(WebClientGreeting.class);
			System.out.println(">> messageG = " + webClientGreeting.webClient4Greet().block());
		}
		else {
			WebClientCity webClientCity = context.getBean(WebClientCity.class);
			System.out.println(">> messageC = " + webClientCity.webClient4City(ID_START).block());
		}
	}
}
