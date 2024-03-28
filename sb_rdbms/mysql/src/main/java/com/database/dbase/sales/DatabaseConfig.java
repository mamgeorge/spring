package com.database.dbase.sales;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

	private static final Properties properties = new Properties();
	private static final String DB_PROPS_FILE = "db.properties";

	static {

		try {
			ClassLoader classLoader = DatabaseConfig.class.getClassLoader();
			InputStream input = classLoader.getResourceAsStream(DB_PROPS_FILE);
			if (input == null) { System.out.println("MISSING PROPERTIES!"); System.exit(1); }
			properties.load(input);
		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}

	public static String getDbUrl() {
		return properties.getProperty("db.url");
	}

	public static String getDbUsername() {
		return properties.getProperty("db.username");
	}

	public static String getDbPassword() {
		return properties.getProperty("db.password");
	}
}