package com.basics.dbsqlite;

import com.intuit.karate.junit5.Karate;

/*
	$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
	$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
	mvn -Dtest=TestRunner test
 */
class TestRunner {

	@Karate.Test Karate testUsers() 
	{ return Karate.run("classpath:test.feature"); }
}