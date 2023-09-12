package com.basics.securing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "SecuringWebApp API", version = "1.0314",
	description = "Some very general Information" ) )
public class SecuringWebApp {

	public static void main(String[] strings) {
		//
		System.out.println("#### SecuringWebApp ####");
		SpringApplication.run(SecuringWebApp.class, strings);
	}
}
