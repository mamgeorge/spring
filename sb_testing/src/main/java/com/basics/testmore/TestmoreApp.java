package com.basics.testmore;

import com.basics.testmore.services.ICountryService;
import com.basics.testmore.util.UtilityMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.logging.Logger;

import static com.basics.testmore.util.UtilityMain.EOL;

// @SpringBootApplication adds @Configuration, @ComponentScan, @EnableWebMvc, @EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class TestmoreApp implements CommandLineRunner {

	public static final String KRB5_CONFIG_FILE = "java.security.krb5.conf";

	@Autowired private ICountryService countryService;
	@Autowired private static Environment environment;

	private static final Logger LOGGER = Logger.getLogger(TestmoreApp.class.getName());
	private static int isStartUpValid = 0;
	private static String txtLines = "";
	private static ConfigurableEnvironment configurableEnvironment;

	public static void main(String[] strings) {

		LOGGER.info("\nTestmoreApplication Starting!");
		SpringApplication springApplication = new SpringApplication(TestmoreApp.class);
		springApplication.setBannerMode(Banner.Mode.CONSOLE);

		ConfigurableApplicationContext caContext = springApplication.run(strings);
		startupCheck(caContext);
		LOGGER.info("\nTestmoreApplication Running!");
	}

	private static void startupCheck(ConfigurableApplicationContext caContext) {

		isStartUpValid = 0;
		configurableEnvironment = caContext.getEnvironment();
		configurableEnvironment.setActiveProfiles("dev");
		checkActivePort();
		checkActiveProfiles();
		checkActiveKrb5();

		if ( isStartUpValid < 2 ) {
			LOGGER.info("#### RESTART WITH PROPER CONFIGURATION! ####\n");
			caContext.close();
		}
	}

	private static void checkActivePort( ) {

		txtLines = "#### TestMoreApp started on port: ";
		String serverPort = configurableEnvironment.getProperty("server.port");
		if ( serverPort == null || serverPort.isEmpty() ) {
			txtLines += "#### NO PORT DETECTED!" + EOL;
		} else {
			txtLines += serverPort + EOL;
			isStartUpValid += 1;
		}
	}

	private static void checkActiveProfiles( ) {

		String[] activeProfilesArray = configurableEnvironment.getActiveProfiles();
		if ( activeProfilesArray == null || activeProfilesArray.length == 0 ) {
			txtLines += "#### NO PROFILES DETECTED... add a 'dev, uat, or prod' profile." + EOL;
		} else {
			String activeProfilesLine = Arrays.toString(activeProfilesArray);
			txtLines += "#### profiles: " + activeProfilesLine + EOL;
			isStartUpValid += 1;
		}
	}

	private static void checkActiveKrb5( ) {

		String krb5Conf = configurableEnvironment.getProperty(KRB5_CONFIG_FILE);
		if ( krb5Conf == null || krb5Conf.isEmpty() ) {

			txtLines += "#### NO KERBEROS DETECTED... add VM arg like: \n\t"
				+ "-Djava.security.krb5.conf=sb_testing/src/main/resources/krb5.conf";
		} else {
			txtLines += "#### krb5Conf: " + krb5Conf;
			isStartUpValid += 1;
		}
		LOGGER.info(EOL + txtLines + EOL);
	}

	@Override public void run(String... strings) {

		if (environment==null) {
			LOGGER.severe("\n\t" + "environment: " + environment);
		} else {
			LOGGER.info("\n\t" + "USERNAME: " + environment.getProperty("USERNAME"));
		}
		countryService.load(UtilityMain.getFileLocal("countries.csv"));
		LOGGER.info("\n\t" + "countryService.getTotal(): " + countryService.getTotal());
	}
}
