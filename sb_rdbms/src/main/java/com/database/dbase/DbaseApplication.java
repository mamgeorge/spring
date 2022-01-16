package com.database.dbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbaseApplication {

	public static void main(String[] args) {
		//
		System.out.println("Greetings from DB");
		SpringApplication.run(DbaseApplication.class, args);
	}
}

