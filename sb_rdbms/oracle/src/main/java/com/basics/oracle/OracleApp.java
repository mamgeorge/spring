package com.basics.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OracleApp {

	public static void main(String[] args) throws Throwable {

		System.out.println("#### SecuringWebApp ####");
		SpringApplication.run(OracleApp.class, args);
		System.out.println("RUNNING!!!");
	}
}
