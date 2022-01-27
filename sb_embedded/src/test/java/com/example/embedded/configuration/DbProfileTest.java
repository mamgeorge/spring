package com.example.embedded.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // required for read_h2, read_h2_full()
public class DbProfileTest {
	//
	// org.springframework.boot.jdbc.DataSourceBuilder vs DriverManager

	private static final String EOL = "\n";
	private static final String DLM = " | ";

	@Autowired private ApplicationContext appContext;
	@Autowired private DataSource dataSource;

	@Test void getBeans( ) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		ConfigurableListableBeanFactory CLBF = ( (AbstractApplicationContext) appContext ).getBeanFactory();
		AtomicInteger ai = new AtomicInteger();
		Object object = null;
		//
		String[] beanNames = appContext.getBeanDefinitionNames();
		Arrays.stream(beanNames).sorted()
			.forEach(str -> stringBuilder.append("\tBDNs: " + ai.incrementAndGet() + " " + str + EOL));
		stringBuilder.append(EOL);
		//
		ai.set(0);
		Arrays.stream(beanNames).sorted()
			.forEach(str -> stringBuilder.append("\tCLBF: " + ai.incrementAndGet() + " "
				+ CLBF.getSingleton(str) + EOL));
		stringBuilder.append(EOL);
		//
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void test_DriverManager( ) {
		//
		StringBuilder stringBuilder = new StringBuilder();
		Enumeration<Driver> enumeration = DriverManager.getDrivers();
		Stream<Driver> stream = StreamSupport.stream(
			Spliterators.spliteratorUnknownSize(enumeration.asIterator(), Spliterator.ORDERED),
			false
		);
		stream.forEach(str -> stringBuilder.append(str).append(EOL));
		//
		stringBuilder.append(EOL).append(DbProfile.testClassNames());
		//
		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}

	@Test void test_DataSourceBuilder( ) {
		//
		String txtLines = "";
		String sqlDefault = "SELECT * FROM cities WHERE id > 0 ORDER BY population ASC";
		try {
			Connection connection = dataSource.getConnection();
			//
			PreparedStatement preparedStatement = connection.prepareStatement(sqlDefault);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColumnCount = resultSetMetaData.getColumnCount();
			while ( resultSet.next() ) {
				for ( int ictr = 1; ictr < intColumnCount + 1; ictr++ ) {
					txtLines += resultSet.getString(ictr) + DLM;
				}
				txtLines += EOL;
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_sqlite( ) {
		//
		DbProfile dbProfile = new DbProfile(DbProfile.DBTYPE.sqlite, "", "");
		String txtLines = dbProfile.readDB("", "");
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_sqlite_full( ) {
		//
		String txtLines = "";
		String dbName = "chinook.db";
		String dbUrl = "jdbc:sqlite:C:/workspace/dbase/sqlite/" + dbName;
		String sqlDefault = "SELECT * FROM employees WHERE BirthDate > '1964-01-01' ORDER BY LastName ASC";
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlDefault);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColumnCount = resultSetMetaData.getColumnCount();
			while ( resultSet.next() ) {
				for ( int ictr = 1; ictr < intColumnCount + 1; ictr++ ) {
					txtLines += resultSet.getString(ictr) + DLM;
				}
				txtLines += EOL;
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_h2( ) {
		//
		// http://h2database.com/html/features.html
		String dbName = "mydb";
		DbProfile dbProfile = new DbProfile(DbProfile.DBTYPE.h2, "", dbName);
		String txtLines = dbProfile.readDB("sa", "qwerty");
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_h2_full( ) {
		//
		String txtLines = "";
		// jdbc:h2:mem:mydb;INIT=create schema if not exists mydb\;runscript from '~/import.sql'
		String dbUrl = "jdbc:h2:mem:mydb;user=sa;password=qwerty;INIT=CREATE SCHEMA IF NOT EXISTS SCHEMA_1";
		String sqlDefault = "SELECT * FROM cities WHERE id > 0 ORDER BY population ASC";
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlDefault);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColumnCount = resultSetMetaData.getColumnCount();
			while ( resultSet.next() ) {
				for ( int ictr = 1; ictr < intColumnCount + 1; ictr++ ) {
					txtLines += resultSet.getString(ictr) + DLM;
				}
				txtLines += EOL;
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}
}
