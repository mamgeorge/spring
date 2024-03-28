package com.example.pgs.demo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTests {

	public static final String MAIN_APP_YAML = "application.yml";

	public static final String DLM = "\t";
	public static final String EOL = "\n";
	public static final int MAXLEN = 20;

	@Test void test_getQuery( ) {

		String sql = "SELECT * FROM film ORDER BY title ASC LIMIT 10;";
		String dbPropsFile = "";
		Properties properties = getPropsYaml(dbPropsFile);
		Connection connection = getConnection(properties);
		String txtLines = getQuery(connection, sql);

		System.out.println(txtLines);
		assertTrue(true);
	}

	// utils
	public static Properties getPropsYaml(String dbPropsFile){

		if(dbPropsFile==null || dbPropsFile.isEmpty()){ dbPropsFile = MAIN_APP_YAML; }
		System.out.println("dbPropsFile: " + dbPropsFile);
		Properties properties = new Properties();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(dbPropsFile);
			if (inputStream==null) {
				System.out.println("ERROR: CANNOT FIND: " + dbPropsFile);
				System.exit(1);
			}
			properties.load(inputStream);
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return properties;
	}

	public static Connection getConnection(Properties properties) {

		Connection connection = null;
		try {
			// Get database credentials from DatabaseConfig class
			String jdbcUrl = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			if (password==null || password.isEmpty()) {
				System.out.println("ERROR: CANNOT FIND password!" );
				System.exit(1);
			}
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return connection;
	}

	public static String getQuery(Connection connection, String sql) {

		StringBuilder stringBuilder = new StringBuilder();

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData rsmData = resultSet.getMetaData();
			AtomicInteger ai = new AtomicInteger();
			while ( resultSet.next() ) {

				stringBuilder.append(String.format("\t%03d ", ai.incrementAndGet()));
				for (int ictr = 1; ictr <= rsmData.getColumnCount(); ictr++) {
					String txtLine = resultSet.getString(ictr);
					if (txtLine==null || txtLine.length()<MAXLEN) { } else {
						txtLine=txtLine.substring(0,MAXLEN);
					}
					stringBuilder.append(txtLine + DLM);
				}
				stringBuilder.append(EOL);
			}
			resultSet.close();
			statement.close();
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return stringBuilder.toString();
	}
}
