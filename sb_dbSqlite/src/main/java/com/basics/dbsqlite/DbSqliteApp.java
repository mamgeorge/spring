package com.basics.dbsqlite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbSqliteApp {

	public static void main(String[] strings) throws Throwable {
		//
		System.out.println("#### SecuringWebApp ####");
		SpringApplication.run(DbSqliteApp.class, strings);
	}
}
