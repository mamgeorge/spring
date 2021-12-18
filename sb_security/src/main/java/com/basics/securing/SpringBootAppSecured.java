package com.basics.securing;

import com.basics.securing.services.ICountryService;
import com.basics.securing.utils.CSVHelper;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringBootAppSecured implements CommandLineRunner { // added CLR for CSV
	//
	private static final Logger LOGGER = Logger.getLogger( SpringBootAppSecured.class.getName( ) );

	@Autowired private ICountryService countryService;
    @Autowired private Environment environment;

	public static void main(String[] strings) throws Throwable {
		//
		SpringApplication.run(SpringBootAppSecured.class, strings);
	}
	
	@Override // added Override for CSV
	public void run(String... strings) throws Exception {
		//
		LOGGER.info( "USERNAME: " + environment.getProperty( "USERNAME" ) );
		countryService.load( CSVHelper.getLocalFile() );
	}
}
