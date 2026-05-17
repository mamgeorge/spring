package com.basics.oracle;

import static com.basics.oracle.ReflectionHelper.DLM;
import static com.basics.oracle.ReflectionHelper.EOL;
import static com.basics.oracle.ReflectionHelper.ERR_PRFX;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DbProfile  {
	
	public enum DBTYPE {sqlite, oracle, oracleTns, postgresql, h2, mssql, mysql, mongodb};

	public enum DBASES {

		// access, dbname, host, port, serviceName, sqlDefault, user, pass
		SQLITE("NONE", "chinook", "C:/workspace/dbase/sqlite/", "", "",
			"SELECT * FROM customers WHERE Country = 'USA' ORDER BY LastName ASC", "", ""),
		ORACLE("NONE", "XE", "localhost", "1521", "", 
			"SELECT * FROM system.employees ORDER BY last_name ASC FETCH FIRST 10 ROWS ONLY", "ORACLE_USER", "ORACLE_PASS"),		
		POSTGRESQL("NONE", "xxxx", "xxxx", "", "",
			"SELECT * FROM customers", "POSTGRES_USER", "POSTGRES_PASS"),
		MYSQL("NONE", "mydb", "localhost", "3306", "",
			"SELECT * FROM mydb.history WHERE id > 0 ORDER BY dateend;", "MYSQL_USER", "MYSQL_PASS"),
		MSSQLSERVER("NONE", "xxxx", "localhost", "1433", "",
			"SELECT * FROM xxxx", "MSSQL_USER", "MSSQL_PASS");

		public final String access;
		public final String dbname;
		public final String host;
		public final String port;
		public final String serviceName;
		public final String sqlDefault;
		public final String user;
		public final String pass;

		DBASES(String access, String dbname, String host, String port, String serviceName, String sqlDefault,
			String user, String pass) {
			this.access = access;
			this.dbname = dbname;
			this.host = host;
			this.port = port;
			this.serviceName = serviceName;
			this.sqlDefault = sqlDefault;
			this.user = user;
			this.pass = pass;
		}
	}	
	public static String getResults(ResultSet resultSet, int intColumnCount) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			// get column names
			stringBuilder.append(EOL);
			for ( int nctr = 1; nctr < intColumnCount + 1; nctr++ ) {
				stringBuilder.append(resultSetMetaData.getColumnName(nctr)).append(DLM);
			}
			// get rows
			Object object;
			stringBuilder.append(EOL);
			while ( resultSet.next() ) {
				stringBuilder.append(DLM);
				for ( int cctr = 1; cctr < intColumnCount + 1; cctr++ ) {
					object = resultSet.getObject(cctr);
					if ( object instanceof Clob ) { object = object.getClass().getName(); }
					if ( object == null ) { object = "NULL"; }
					if ( cctr < intColumnCount ) { stringBuilder.append(object).append(DLM); } else {
						stringBuilder.append(object);
					}
				}
				stringBuilder.append(EOL);
			}
		}
		catch (SQLException ex) { System.out.println(ERR_PRFX + ex.getMessage()); }
		return stringBuilder.toString();
	}
}
