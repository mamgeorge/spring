package com.database.dbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RdbmsApplication {

	public static void main(String[] args) {

		System.out.println("Greetings from DB");
		SpringApplication.run(RdbmsApplication.class, args);
	}
}

