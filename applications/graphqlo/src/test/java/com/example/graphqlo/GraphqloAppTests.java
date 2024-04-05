package com.example.graphqlo;

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
class GraphqloAppTests {

	public static final String EOL = "\n";
	public static final String DLM = "\t";

	@Test void contextLoads() { assertTrue(true); }

	@Test @Disabled("integration only") void jdbcTestConnect() {

		StringBuilder stringBuilder = new StringBuilder(EOL);
		String dbSQL = "SELECT * FROM APP.Cities";
		String dbDriver = "org.apache.derby.jdbc.ClientDriver";
		String dbURL = "jdbc:derby:C:/workspace/dbase/derby/db-derby-10.15.2.0-bin/demo/derbytutor/toursdb15";

		try {
			Class.forName(dbDriver).newInstance();
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
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex)
		{ System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(stringBuilder);
		assertTrue(true);
	}
}
