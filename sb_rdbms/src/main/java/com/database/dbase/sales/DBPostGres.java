package com.database.dbase.sales;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPostGres {

	public static final String DLM = "\t";
	public static final String EOL = "\n";
	public static final int MAXLEN = 20;

	public static String getQuery(String schema, String sql) {

		StringBuilder stringBuilder = new StringBuilder();

		try {
			Connection connection = getConnection(schema);
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

	public static String getPrepared(String schema, String sql, Object[] args) {

		StringBuilder stringBuilder = new StringBuilder();

		try {
			Connection connection = getConnection(schema);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			int actr = 0;
			if(args[actr] instanceof String ) { preparedStatement.setString(1, args[actr].toString()); }
			if(args[actr] instanceof Integer ) { preparedStatement.setInt(1, Integer.parseInt(args[actr].toString())); }
			if(args[actr] instanceof Date ) { preparedStatement.setDate(1, (Date) args[actr] ); }

			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData rsmData = resultSet.getMetaData();
			AtomicInteger ai = new AtomicInteger();
			while ( resultSet.next() ) {

				stringBuilder.append(String.format("\t%03d ", ai.incrementAndGet()));
				for (int ictr = 1; ictr < rsmData.getColumnCount(); ictr++) {
					String txtLine = resultSet.getString(ictr);
					if (txtLine==null || txtLine.length()<MAXLEN) { } else {
						txtLine=txtLine.substring(0,MAXLEN);
					}
					stringBuilder.append(txtLine + DLM);
				}
				stringBuilder.append(EOL);
			}
			resultSet.close();
			preparedStatement.close();
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return stringBuilder.toString();
	}

	public static Connection getConnection(String schema) throws SQLException {

		Connection connection = null;
		try {
			// Get database credentials from DatabaseConfig class
			var jdbcUrl = DatabaseConfig.getDbUrl() + schema;
			var username = DatabaseConfig.getDbUsername();
			var password = DatabaseConfig.getDbPassword();

			// Open a connection
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return connection;
	}
}
