package com.basics.securing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecuringWebApp {

	public static void main(String[] strings) throws Throwable {
		//
		System.out.println("#### SecuringWebApp ####");
		SpringApplication.run(SecuringWebApp.class, strings);
	}
}
