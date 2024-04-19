package com.example.graphqlp;

import lombok.Getter;
import lombok.Setter;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.example.graphqlp.DbProfile.DBASE.DRB;
import static com.example.graphqlp.DbProfile.DBASE.PGS;

@Getter @Setter
public class DbProfile {

	enum DBASE {DRB, PGS}

	public DbProfile dbProfile;
	private Properties props;
	private Driver driver;
	private String dburl;
	private String dbName;
	private String user;
	private String pass;

	public static final Map<DbProfile.DBASE, Driver> DRIVERS = new HashMap<>();
	static {
		DRIVERS.put(DRB, new org.apache.derby.jdbc.EmbeddedDriver());
		DRIVERS.put(PGS, new org.postgresql.Driver());
	}

	public static final Map<DbProfile.DBASE, String> DBURLS = new HashMap<>();
	static {
		DBURLS.put(DRB,	"jdbc:derby:C:/workspace/dbase/derby/db-derby-10.15.2.0-bin/demo/derbytutor/toursdb15" );
		DBURLS.put(PGS, "jdbc:postgresql://localhost:5432/dvdrental" );
	}

	public DbProfile() {}

	public DbProfile(DBASE dbase, String dbName, String user, String pass) {

		this.dbProfile = new DbProfile();
		this.driver = DRIVERS.get(dbase);
		this.dburl = DBURLS.get(dbase);
		props = new Properties();
		props.setProperty("dataSource", dburl);
		props.setProperty("database", dbName);
		props.setProperty("user", user);
		props.setProperty("password", pass);
		props.setProperty("jdbcDriver", driver.toString());
	}
}
