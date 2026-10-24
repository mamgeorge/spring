package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
	$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
	$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
	mvn -Dtest=TestRunner test
 */
class TestRunner {

	enum TESTFILES {
		tests, https, posts, examples, reusability, login, switches, codeuse, hooks
	}

	private static final Logger LOGGER = Logger.getLogger(TestRunner.class.getName());

	public static final String PROJECT = "examples";
	public static final String PREFIX = "classpath:examples/";
	public static final String FILENAME = TESTFILES.hooks.name();
	public static final String EXT = ".feature";
	public static final String PATHFILE = PREFIX ;//+ FILENAME + EXT;
	public static final int PARALLEL_LIMIT = 5;

	@Test void testParallel() {

		// @parallel=false can be used in feature file to block parallel
		Results results = Runner.path(PATHFILE)
				.outputHtmlReport(true)
				.outputCucumberJson(true)
				.parallel(PARALLEL_LIMIT);
		
		LOGGER.info("getReportDir: " + results.getReportDir());
		generateReport(results.getReportDir());
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
		try {
			karate.forEach(k -> sb.append(k.getDisplayName() + " - " + k.toString()).append(EOL));
		} catch (Exception ex) {
			LOGGER.severe("ERROR: " + ex.getMessage());
		}
		LOGGER.info("karate: " + sb.toString());

		return karate;
	}

	@Karate.Test Karate test_any() {

		return Karate.run(PATHFILE)
				.relativeTo(getClass())
				.tags("~@ignore")
				.karateEnv("dev");
	}

	public static void generateReport(String karateOutputPath) {

		File filePath = new File(karateOutputPath);
		Collection<File> collection 
			= FileUtils.listFiles(filePath, new String[] { "json" }, true);
		List<String> listJsons = new ArrayList<>(collection.size());
		collection.forEach(file -> listJsons.add(file.getAbsolutePath()));
		
		File fileTarget = new File(karateOutputPath);
		Configuration configuration = new Configuration(fileTarget, PROJECT);
		ReportBuilder reportBuilder = new ReportBuilder(listJsons, configuration);
		reportBuilder.generateReports();
	}
}