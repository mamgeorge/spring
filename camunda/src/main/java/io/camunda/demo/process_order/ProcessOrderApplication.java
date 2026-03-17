package io.camunda.demo.process_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessOrderApplication {

	public static void main(String[] args) {

		System.out.println("#### ProcessOrderApplication");
		SpringApplication.run(ProcessOrderApplication.class, args);
	}

}
