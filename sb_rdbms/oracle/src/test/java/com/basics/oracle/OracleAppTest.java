package com.basics.oracle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.basics.oracle.ReflectionHelper.exposeObject;

// @RunWith(SpringRunner.class) // adds beans
// @ContextConfiguration( classes = { } )
// @ActiveProfiles("local")
// @SpringBootTest // creates applicationContext
@SpringBootTest( classes = OracleAppTest.class )
class OracleAppTest {

	@Autowired ApplicationContext appContext;
	@Autowired Environment environment;
	public static final String FRMT = "\t%-20s [%s]\n";

	@Test void basic( ) {

		OracleApp app = new OracleApp();
		try { OracleApp.main(new String[] {}); } 
		catch (Throwable ex) { System.out.println("ERROR: " + ex.getMessage()); }
		assertNotNull(app);
	}

	@Test void contextLoads( ) {

		StringBuilder sb = new StringBuilder();
		sb.append(exposeObject(appContext)).append(EOL);
		sb.append(exposeObject(environment)).append(EOL);
		sb.append(EOL);
		for ( String profile : environment.getDefaultProfiles() ) {
			sb.append(String.format(FRMT, "profileDEF: ", profile)).append(EOL);
		}
		System.out.println(sb);
		assertNotNull(sb);
	}
}

