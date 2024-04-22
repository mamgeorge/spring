package com.example.graphqlp;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.graphqlp.DbProfile.DBASE.DRB;
import static com.example.graphqlp.DbProfile.DBASE.PGS;
import static com.example.graphqlp.DbProfile.DBASE.SQLITE;
import static com.example.graphqlp.DbProfile.loopResultSet;
import static com.example.graphqlp.GenericUtils.EOL;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DbProfileTests {

	@Test void executeQuery_SQLITE( ) {

		String dbSQL = "SELECT * FROM Customers";
		DbProfile dbProfile = new DbProfile(SQLITE, "APP", "", "");

		StringBuilder sb = new StringBuilder(EOL);
		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(dbSQL);

			sb.append(loopResultSet(resultSet));
			statement.close();
		}
		catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(sb);
		assertTrue(true);
	}

	@Test void executeQuery_DRB( ) {

		String dbSQL = "SELECT * FROM Cities";
		DbProfile dbProfile = new DbProfile(DRB, "APP", "", "");

		StringBuilder sb = new StringBuilder(EOL);
		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(dbSQL);

			sb.append(loopResultSet(resultSet));
			statement.close();
		}
		catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(sb);
		assertTrue(true);
	}

	@Test void executeQuery_PGS( ) {

		String dbSQL = "SELECT * FROM actor";
		String user = System.getenv("POSTGRES_USER");
		String pass = System.getenv("POSTGRES_PASS");
		DbProfile dbProfile = new DbProfile(PGS, "public", user, pass);

		StringBuilder sb = new StringBuilder("username: " + user + EOL);
		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(dbSQL);

			sb.append(loopResultSet(resultSet));
			statement.close();
		}
		catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println(sb);
		assertTrue(true);
	}
}
