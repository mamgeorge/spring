package com.basics.oracle;

import static com.basics.oracle.ReflectionHelper.DLM;
import static com.basics.oracle.ReflectionHelper.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.jupiter.api.Test;

public class DBTest {

	// connections & clients
	@Test void connection_sqlite( ) {
		//
		StringBuilder stringBuilder = new StringBuilder(EOL);
		String dbName = DbProfile.DBASES.SQLITE.dbname + ".db";
		String dbUrl = "jdbc:sqlite:" + DbProfile.DBASES.SQLITE.host + dbName;
		String sqlDefault = DbProfile.DBASES.SQLITE.sqlDefault;
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlDefault);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColumnCount = resultSetMetaData.getColumnCount();
			while ( resultSet.next() ) {
				for ( int ictr = 1; ictr < intColumnCount + 1; ictr++ ) {
					stringBuilder.append(resultSet.getString(ictr)).append(DLM);
				}
				stringBuilder.append(EOL);
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println("stringBuilder: " + stringBuilder);
		assertTrue(stringBuilder.length() > 1);
	}

	@Test void connection_oracle( ) {

		String txtLines = EOL;
		String dbName = DbProfile.DBASES.ORACLE.dbname;
		String host = DbProfile.DBASES.ORACLE.host;
		String port = DbProfile.DBASES.ORACLE.port;
		String sqlDefault = DbProfile.DBASES.ORACLE.sqlDefault;
		String dbUrl = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
		String username = System.getenv(DbProfile.DBASES.ORACLE.user);
		String password = System.getenv(DbProfile.DBASES.ORACLE.pass);
		System.out.println("dbUrl: " + dbUrl);
		System.out.println("credentials: " + username + " / " + password);
		
		Properties props = new Properties();
        props.put("user", username);
        props.put("password", password);
        props.put("internal_logon", "sysdba");
		//
		try {
			Class.forName(oracle.jdbc.OracleDriver.class.getName());
			Connection connection = DriverManager.getConnection(dbUrl,props);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlDefault);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColumnCount = resultSetMetaData.getColumnCount();
			txtLines += DbProfile.getResults(resultSet, intColumnCount);
		}
		catch (ClassNotFoundException | SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		//
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}
}
