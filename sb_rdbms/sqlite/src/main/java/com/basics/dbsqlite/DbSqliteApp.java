package com.basics.dbsqlite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DbSqliteApp {

	public static void main(String[] strings) throws Throwable {

		System.out.println("#### DbSqliteApp started! ####");
		SpringApplication.run(DbSqliteApp.class, strings);
	}
}
@Component class AppStartupRunner {

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
		System.out.println("#### DbSqliteApp running! ####");    }
}
