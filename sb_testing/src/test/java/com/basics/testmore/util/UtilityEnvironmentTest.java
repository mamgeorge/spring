package com.basics.testmore.util;

import com.basics.testmore.configuration.AnySpringFoxConfig;
import com.basics.testmore.configuration.BeanConfiguration;
import com.basics.testmore.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static com.basics.testmore.util.UtilityMainTest.ASSERT_MSG;

@ExtendWith(SpringExtension.class) // adds beans
@ContextConfiguration(classes = {AnySpringFoxConfig.class, BeanConfiguration.class})
@ActiveProfiles("local")
@SpringBootTest // creates applicationContext
public class UtilityEnvironmentTest {
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

	@Test public void contextLoads() {
		//
		String txtLines = "";
		//
		txtLines += String.format(FRMTVAR, "appContext.getId", appContext.getId());
		txtLines += String.format(FRMTVAR, "appname", appname);
		//
		System.out.println(txtLines);
		Assert.notNull(appContext, ASSERT_MSG);
		Assert.notNull(appname.contains("LOCAL"), ASSERT_MSG);
	}

	@Test public void environmentVars() {
		//
		String txtLines = "";
		//
		txtLines += String.format(FRMTVAR, "ENV.getProperty", environment.getProperty(PROP_APPNAME));
		txtLines += String.format(FRMTVAR, "CON.getProperty", configurableEnvironment.getProperty(PROP_APPNAME));
		String txtDB = environment.getProperty(PROP_DBNAME);
		txtLines += String.format(FRMTVAR, PROP_DBNAME, txtDB);
		//
		System.out.println(txtLines);
		Assert.isTrue(appname.contains("TestmoreApplication"), ASSERT_MSG);
	}

	@Test public void beanList() {
		//
		StringBuffer stringBuffer = new StringBuffer();
		String[] stringBeans = appContext.getBeanDefinitionNames();
		AtomicInteger aint = new AtomicInteger();
		for (String bean : stringBeans) {
			stringBuffer.append(String.format(FRMTSML, aint.incrementAndGet(), bean));
		}
		//
		System.out.println(stringBuffer);
		Assert.isTrue(stringBeans.length >= 10, ASSERT_MSG);
	}

	@Test public void beanAccess() {
		//
		// must have @ContextConfiguration(classes = {BeanConfiguration.class})
		City city = (City) appContext.getBean("cityBean");
		System.out.println(city);
		Assert.isTrue(city.getName().contains("George"), ASSERT_MSG);
	}

	@Test public void bean_addContext() {
		//
		String GEORGE = "MARTIN";
		AutowireCapableBeanFactory ACBF = appContext.getAutowireCapableBeanFactory();
		ACBF.createBean(String.class);
		ACBF.initializeBean(GEORGE, "george");
		ACBF.autowireBean(GEORGE);
		ACBF.applyBeanPostProcessorsAfterInitialization(GEORGE, "george");
		//ACBF.configureBean(GEORGE, "george");
		//
		// show it
		StringBuffer stringBuffer = new StringBuffer();
		String[] stringBeans = appContext.getBeanDefinitionNames();
		AtomicInteger aint = new AtomicInteger();
		Arrays.stream(stringBeans).sequential().forEach(city ->
				stringBuffer.append(String.format(FRMTSML, aint.incrementAndGet(), city)));
		//
		System.out.println(stringBuffer);
		Assert.isTrue(stringBeans.length >= 10, ASSERT_MSG);
	}
}