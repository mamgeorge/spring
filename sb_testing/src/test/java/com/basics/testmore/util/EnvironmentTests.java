package com.basics.testmore.util;

import com.basics.testmore.configuration.SpringFoxConfig;
import com.basics.testmore.configuration.BeanConfiguration;
import com.basics.testmore.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.testmore.util.UtilityMainTests.ASSERT_MSG;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class) // adds beans
@ContextConfiguration(classes = { SpringFoxConfig.class, BeanConfiguration.class})
@ActiveProfiles("local")
@SpringBootTest // creates applicationContext
public class EnvironmentTests {
	//
	@Autowired private ApplicationContext appContext;
	@Autowired private Environment environment;
	@Autowired private ConfigurableEnvironment configurableEnvironment;
	@Autowired @Qualifier("CityBean") private City city;
	@Value("${app.name}") private String appname;

	public static final String PROP_APPNAME = "app.name";
	public static final String PROP_DBNAME = "datasource.platform";
	public static final String FRMTVAR = "\t%-20s [%s]\n";
	public static final String FRMTSML = "\t%02d\t%s\n";

	@Test void contextLoads() {
		//
		String txtLines = "";
		//
		txtLines += String.format(FRMTVAR, "appContext.getId", appContext.getId());
		txtLines += String.format(FRMTVAR, "appname", appname);
		//
		System.out.println(txtLines);
		assertNotNull(appContext, ASSERT_MSG);
		assertTrue(appname.contains("LOCAL"), ASSERT_MSG);
	}

	@Test void environmentVars() {
		//
		String txtLines = "";
		//
		txtLines += String.format(FRMTVAR, "ENV.getProperty", environment.getProperty(PROP_APPNAME));
		txtLines += String.format(FRMTVAR, "CON.getProperty", configurableEnvironment.getProperty(PROP_APPNAME));
		String txtDB = environment.getProperty(PROP_DBNAME);
		txtLines += String.format(FRMTVAR, PROP_DBNAME, txtDB);
		//
		System.out.println(txtLines);
		assertTrue(appname.contains("TestmoreApplication"), ASSERT_MSG);
	}

	@Test void beansList() {
		//
		String beansList = getBeansList(appContext);
		System.out.println(beansList);
		int beansLen = appContext.getBeanDefinitionNames().length;
		assertTrue(beansLen >= 10, ASSERT_MSG);
	}

	@Test void beanAccess() {
		//
		// must have @ContextConfiguration(classes = {BeanConfiguration.class})
		City city = (City) appContext.getBean("cityBean");
		System.out.println(city);
		assertTrue(city.getName().contains("George"), ASSERT_MSG);
	}

	@Test void bean_addContext() {
		//
		/*
			https://www.javaprogramto.com/2019/07/spring-dynamically-register-beans.html
			Local Dynamic Bean Registration:
			1 with GenericBeanDefinition in DefaultListableBeanFactory context
			2 with BeanDefinitionBuilder in DefaultListableBeanFactory beanFactory
			3 with GenericBeanDefinition in ConfigurableListableBeanFactory beanFactory
			4 with GenericBeanDefinition in BeanDefinitionRegistry
		*/
		String GEORGE = "MARTIN";
		AutowireCapableBeanFactory ACBF = appContext.getAutowireCapableBeanFactory();
		ACBF.createBean(String.class);
		ACBF.initializeBean(GEORGE, "george");
		ACBF.autowireBean(GEORGE);
		ACBF.applyBeanPostProcessorsAfterInitialization(GEORGE, "george");
		// ACBF.configureBean(GEORGE, "george");
		//
		ConfigurableListableBeanFactory CLBF = ((ConfigurableApplicationContext) appContext).getBeanFactory();
		CLBF.registerSingleton(GEORGE.getClass().getCanonicalName(), GEORGE);
		//
		System.out.println(getBeansList(appContext));
		assertTrue(appContext.getBeanDefinitionNames().length >= 10, ASSERT_MSG);
	}

	// #### STATICS ####
	public static String getBeansList(ApplicationContext appContext) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		String[] stringBeans = appContext.getBeanDefinitionNames();
		AtomicInteger aint = new AtomicInteger();
		for (String bean : stringBeans) {
			stringBuilder.append(String.format(FRMTSML, aint.incrementAndGet(), bean));
		}
		//
		return stringBuilder.toString();
	}
}
