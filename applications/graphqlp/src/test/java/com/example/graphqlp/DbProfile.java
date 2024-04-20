package com.example.graphqlp;

import lombok.Getter;
import lombok.Setter;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.example.graphqlp.DbProfile.DBASE.DRB;
import static com.example.graphqlp.DbProfile.DBASE.MYSQL;
import static com.example.graphqlp.DbProfile.DBASE.ORACLE;
import static com.example.graphqlp.DbProfile.DBASE.PGS;
import static com.example.graphqlp.DbProfile.DBASE.SQLITE;

@Getter @Setter
public class DbProfile {

	enum DBASE {DRB, MYSQL, ORACLE, PGS, SQLITE}

	public DbProfile dbProfile;
	private Properties props;
	private Driver driver;
	private String dburl;
	private String dbName;
	private String user;
	private String pass;

	public static final Map<DbProfile.DBASE, Driver> DRIVERS = new HashMap<>();

	static {
		try {
			DRIVERS.put(DRB, new org.apache.derby.jdbc.EmbeddedDriver());
			DRIVERS.put(MYSQL, new com.mysql.cj.jdbc.Driver()); // com.mysql.jdbc.Driver()
			DRIVERS.put(ORACLE, new oracle.jdbc.driver.OracleDriver()); // oracle.jdbc.OracleDriver
			DRIVERS.put(PGS, new org.postgresql.Driver());
			DRIVERS.put(SQLITE, new org.sqlite.JDBC());
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}

	public static final Map<DbProfile.DBASE, String> DBURLS = new HashMap<>();

	static {
		DBURLS.put(DRB,
			"jdbc:derby:C:/workspace/dbase/derby/db-derby-10.15.2.0-bin/demo/derbytutor/toursdb15");
		DBURLS.put(MYSQL, "jdbc:mysql://localhost:3306/mydb");
		DBURLS.put(ORACLE, "jdbc:oracle:thin:@localhost:1521:XE");
		DBURLS.put(PGS, "jdbc:postgresql://localhost:5432/dvdrental");
		DBURLS.put(SQLITE, "jdbc:sqlite:C:/workspace/dbase/sqlite/chinook.db");
	}

	public DbProfile( ) { }

	public DbProfile(DBASE dbase, String dbName, String user, String pass) {

		this.dbProfile = new DbProfile();
		this.driver = DRIVERS.get(dbase);
		this.dburl = DBURLS.get(dbase);
		props = new Properties();
		props.setProperty("dataSource", dburl);
		if ( dbase.equals(ORACLE) ) { } else { props.setProperty("database", dbName); }
		props.setProperty("user", user);
		props.setProperty("password", pass);
		props.setProperty("jdbcDriver", driver.toString());
	}
}
