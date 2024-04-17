package com.example.graphqlp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

// @SpringBootTest
class GraphqlpAppTests {

	public static final String EOL = "\n";
	public static final String DLM = "\t";

	@Test void contextLoads() { assertTrue(true); }

	@Test @Disabled("integration only") void derby_ClientDriver() {

		StringBuilder stringBuilder = new StringBuilder(EOL);
		String dbSQL = "SELECT * FROM APP.Cities";
		String dbDriver = "org.apache.derby.jdbc.ClientDriver";
		String dbURL = "jdbc:derby:C:/workspace/dbase/derby/db-derby-10.15.2.0-bin/demo/derbytutor/toursdb15";

		try {
			// Class.forName(dbDriver).newInstance();
			DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
			Connection connection = DriverManager.getConnection(dbURL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(dbSQL);
			ResultSetMetaData rsmData = resultSet.getMetaData();
			int columns = rsmData.getColumnCount();

			while (resultSet.next()) {
				for (int ictr = 1; ictr < columns; ictr++) {
					stringBuilder.append(resultSet.getString(ictr)).append(DLM);
				}
				stringBuilder.append(EOL);
			}
			statement.close();
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(stringBuilder);
		assertTrue(true);
	}

	@Test @Disabled("integration only") void derby_EmbeddedDriver() {

		StringBuilder stringBuilder = new StringBuilder(EOL);
		String dbSQL = "SELECT * FROM APP.Cities";
		String dbURL = "jdbc:derby:C:/workspace/dbase/derby/db-derby-10.15.2.0-bin/demo/derbytutor/toursdb15";

		try {
			DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
			Connection connection = DriverManager.getConnection(dbURL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(dbSQL);
			ResultSetMetaData rsmData = resultSet.getMetaData();
			int columns = rsmData.getColumnCount();

			while (resultSet.next()) {
				for (int ictr = 1; ictr < columns; ictr++) {
					stringBuilder.append(resultSet.getString(ictr)).append(DLM);
				}
				stringBuilder.append(EOL);
			}
			statement.close();
		}
		catch (SQLException ex)
		{ System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(stringBuilder);
		assertTrue(true);
	}

	@Test @Disabled("integration only") void pgs_driverManager() {

		StringBuilder stringBuilder = new StringBuilder(EOL);
		String dbSQL = "SELECT * FROM public.actor";
		String dbURL = "jdbc:postgresql://localhost:5432/dvdrental";
		String username = System.getenv("POSTGRES_USER");
		String password = System.getenv("POSTGRES_PASS");
		System.out.println("username: " + username);

		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(dbSQL);
			ResultSetMetaData rsmData = resultSet.getMetaData();
			int columns = rsmData.getColumnCount();

			while (resultSet.next()) {
				for (int ictr = 1; ictr < columns; ictr++) {
					stringBuilder.append(resultSet.getString(ictr)).append(DLM);
				}
				stringBuilder.append(EOL);
			}
			statement.close();
		}
		catch (SQLException ex)
		{ System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(stringBuilder);
		assertTrue(true);
	}
}

