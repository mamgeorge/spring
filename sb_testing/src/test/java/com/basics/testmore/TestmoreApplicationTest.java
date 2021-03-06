package com.basics.testmore;

import com.basics.testmore.util.UtilityMain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

// @RunWith(SpringRunner.class) // adds beans
@ContextConfiguration(classes = {})
// @ActiveProfiles("local")
@SpringBootTest // creates applicationContext
// @SpringBootTest(classes = TestmoreApplicationTest.class)
public class TestmoreApplicationTest {
	//
	@Autowired ApplicationContext appContext;
	@Autowired Environment environment;
	public static final String FRMT = "\t%-20s [%s]\n";

	@Test public void contextLoads() {
		//
		String txtLines = "";
		//
		txtLines += String.format(FRMT, "appContext", appContext);
		txtLines += String.format(FRMT, "environment", environment);
		txtLines += "\n";
		for (String profile : environment.getDefaultProfiles()) {
			txtLines += String.format(FRMT, "profileDEF: ", environment);
		}
		txtLines += UtilityMain.exposeObject(appContext) + "\n";
		txtLines += UtilityMain.exposeObject(environment);
		//
		System.out.println(txtLines);
	}
}
