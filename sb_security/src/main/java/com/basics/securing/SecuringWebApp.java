package com.basics.securing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info( title = "SecuringWebApp API", version = "1.0", description = "Information" ) )
public class SecuringWebApp {

	public static void main(String[] strings) throws Throwable {
		//
		System.out.println("#### SecuringWebApp ####");
		SpringApplication.run(SecuringWebApp.class, strings);
	}
}
