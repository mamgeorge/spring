package com.example.graphqlp;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.graphqlp.DbProfile.DBASE.DRB;
import static com.example.graphqlp.DbProfile.DBASE.PGS;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @Disabled( "integration only" )
class GraphqlpAppTests {

	public static final String EOL = "\n";
	public static final String DLM = "\t";

	@Test void contextLoads( ) { assertTrue(true); }

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

	// utilities
	public static StringBuilder loopResultSet(ResultSet resultSet) {

		StringBuilder sb = new StringBuilder(EOL);
		try {
			ResultSetMetaData rsmData = resultSet.getMetaData();
			int columns = rsmData.getColumnCount();
			while ( resultSet.next() ) {
				for ( int ictr = 1; ictr < columns; ictr++ ) {
					sb.append(resultSet.getString(ictr)).append(DLM);
				}
				sb.append(EOL);
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return sb;
	}
}

