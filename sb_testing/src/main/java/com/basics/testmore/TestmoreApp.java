package com.basics.testmore;

import com.basics.testmore.services.ICountryService;
import com.basics.testmore.util.UtilityMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

// @SpringBootApplication adds @Configuration, @ComponentScan, @EnableWebMvc, @EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class TestmoreApp implements CommandLineRunner {
	//
	private static final Logger LOGGER = Logger.getLogger( TestmoreApp.class.getName( ) );

	@Autowired ICountryService countryService;
	@Autowired private Environment environment;

	public static void main( String[ ] strings ) {
		//
		System.out.println( "HELLO TestmoreApplication!" );
		SpringApplication springApplication = new SpringApplication( TestmoreApp.class );
		springApplication.setBannerMode( Banner.Mode.CONSOLE );
		springApplication.run( strings );
	}

	@Override public void run( String... strings ) throws Exception {
		//
		LOGGER.info( "USERNAME: " + environment.getProperty( "USERNAME" ) );
		countryService.load( UtilityMain.getFileLocal("countries.csv") );
	}
}