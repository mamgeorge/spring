package examples;

import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
	$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
	$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
	mvn -Dtest=TestRunner test
 */
class TestRunner {

	enum TESTFILES { tests, https, posts, examples, reusability, login, switches, codeuse }

	private static final Logger LOGGER = Logger.getLogger(TestRunner.class.getName());

	public static final String PREFIX = "classpath:examples/";
	public static final String FILENAME = TESTFILES.codeuse.name();
	public static final String EXT = ".feature";
	public static final String PATHFILE = PREFIX + FILENAME + EXT;
	public static final int PARALLEL_LIMIT = 5;

	@Test void testParallel() {

		Results results = Runner.path(PATHFILE)
			.outputHtmlReport(true)
			.parallel(PARALLEL_LIMIT);
		assertEquals(0, results.getFailCount(), results.getErrorMessages());
	}

	@Test void test_junit() {

		Results results = Runner.path(PATHFILE)
			.outputHtmlReport(true)
			.parallel(PARALLEL_LIMIT);

		System.out.println("FILE_TESTS: " + PATHFILE);
		System.out.println("JUnit Scenarios Total: " + results.getScenariosTotal());
		System.out.println("JUnit Scenarios Failed: " + results.getScenariosFailed());
		assertTrue(results.getScenariosFailed() == 0);
	}

	@Karate.Test Karate test_logs() {

		Karate karate = Karate.run(PATHFILE).tags("~@ignore");

		StringBuilder sb = new StringBuilder(EOL + "Karate Scenarios: " + EOL);
		try { karate.forEach(k -> sb.append(k.getDisplayName() + " - " + k.toString()).append(EOL));}
		catch (Exception ex) { LOGGER.severe("ERROR: " + ex.getMessage()); }
		LOGGER.info("karate: " + sb.toString());

		return karate;
	}

	@Karate.Test Karate test_any() {

		return Karate.run(PATHFILE)
			.relativeTo(getClass())
			.tags("~@ignore")
			.karateEnv("dev")
			;
	}
}