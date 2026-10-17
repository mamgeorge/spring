package examples;

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

	public static final String PREFIX = "classpath:examples/";
	public static final String EXT = ".feature";
	public static final String FILE_TESTS = PREFIX + "tests" + EXT;
	public static final String FILE_HTTPS = PREFIX + "https" + EXT;
	public static final String FILE_POSTS = PREFIX + "posts" + EXT;
	public static final String FILE_EXAMPLES = PREFIX + "examples" + EXT;

	public static final int PARALLEL_LIMIT = 5;

	@Test void testParallel() {

		Results results = Runner.path(FILE_TESTS).outputHtmlReport(true).parallel(PARALLEL_LIMIT);
		assertEquals(0, results.getFailCount(), results.getErrorMessages());
	}

	@Test void test_junit() {

		Results results = Runner.path(FILE_TESTS).outputHtmlReport(true).parallel(PARALLEL_LIMIT);

		System.out.println("FILE_TESTS: " + FILE_TESTS);
		System.out.println("JUnit Scenarios Total: " + results.getScenariosTotal());
		System.out.println("JUnit Scenarios Failed: " + results.getScenariosFailed());
		assertTrue(results.getScenariosFailed() == 0);
	}
	
	@Karate.Test Karate test_all() {
		return Karate.run(PREFIX);
	}

	@Karate.Test Karate test_tests() {

		Karate karate = Karate.run(FILE_TESTS);

		StringBuilder sb = new StringBuilder(EOL + "Karate Scenarios: " + EOL);
		karate.forEach(k -> sb.append(k.getDisplayName() + " - " + k.toString()).append(EOL));
		System.out.println("karate: " + sb.toString());

		return karate;
	}

	@Karate.Test Karate test_https() {
		return Karate.run(FILE_HTTPS);
	}

	@Karate.Test Karate test_posts() {
		return Karate.run(FILE_POSTS);
	}

	@Karate.Test Karate test_examples() {
		return Karate.run(PREFIX + "examples" + EXT);
	}
}