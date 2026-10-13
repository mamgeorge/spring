package com.basics.dbsqlite;

import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
	$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
	$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
	mvn -Dtest=TestRunner test
 */
class TestRunner {

	public static final String FILE_TEST = "test.feature";

	@Karate.Test Karate testUsers() 
	{ 
		Karate karate = Karate.run("classpath:" + FILE_TEST); 

		StringBuilder sb = new StringBuilder(EOL + "Karate Scenarios: " + EOL);
		karate.forEach(k -> sb.append(k.getDisplayName() + " - " + k.toString()).append(EOL));
		System.out.println("karate: " + sb.toString());

		return karate;
	}

	@Test void testAll() {

        Results results = Runner.path("classpath:" + FILE_TEST)	
            .outputHtmlReport(true)
            .parallel(5);

		System.out.println("Scenarios Total: " + results.getScenariosTotal());
		System.out.println("Scenarios Failed: " + results.getScenariosFailed());
        assertTrue(results.getScenariosFailed() == 0);
    }
}