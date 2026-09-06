package com.basics.dbsqlite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.basics.dbsqlite.model.Customer;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DbSqliteAppTest.class)
class DbSqliteAppTest {

	enum SQL_QUERIES {
		SQL_CUSTOMERS, SQL_EMPLOYEES, SQL_INVOICES, SQL_PLAYLISTS
	};

	public static final String FRMT = "\t%-20s [%s]\n";
	public static final String DB_DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:C:/workspace/dbase/sqlite/chinook.db";
	public static final String DB_USER = "";
	public static final String DB_PASS = "";
	public static final String PATH_SQLS = "samples.sql";

	@Test void contextLoads() {	}

	@Test void read_driverManager() { // uses getSqlQuery

		String SQL_QUERY = getSqlQuery(SQL_QUERIES.SQL_CUSTOMERS.name(), PATH_SQLS);
		StringBuilder sb = new StringBuilder();
		try {
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_QUERY);

			sb.append(getRows(resultSet));
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test void read_jdbcTemplate_query_BPRM() {

		String SQL_QUERY = getSqlQuery(SQL_QUERIES.SQL_CUSTOMERS.name(), PATH_SQLS);
		DataSource dataSource = getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<Customer> customers = jdbcTemplate.query(SQL_QUERY, new BeanPropertyRowMapper<>(Customer.class));

		try { dataSource.getConnection().close(); }
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		// no customerIds if SQL uses rownumber!
		StringBuilder sb = new StringBuilder();
		for (Customer customer : customers) {
			sb.append(String.format("\t%s\t%s\t%s\t%s\n",
					customer.getCustomerid(),
					customer.getFirstname(),
					customer.getLastname(),
					customer.getAddress()));
		}

		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test void read_jdbcTemplate_query_FP() {

		String SQL_QUERY = getSqlQuery(SQL_QUERIES.SQL_CUSTOMERS.name(), PATH_SQLS);
		DataSource dataSource = getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		StringBuilder sb = new StringBuilder();
		jdbcTemplate.query(SQL_QUERY, (resultSet) -> {
			int intColCount = resultSet.getMetaData().getColumnCount();
			for (int jctr = 1; jctr <= intColCount; jctr++) {
				Object object = resultSet.getObject(jctr);
				object = (object == null) ? "NULL" : object;
				sb.append(String.format("\t%s", object));
			}
			sb.append(EOL);
		});

		try { dataSource.getConnection().close(); }
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test void read_jdbcTemplate_queryForList() {

		String SQL_QUERY = getSqlQuery(SQL_QUERIES.SQL_CUSTOMERS.name(), PATH_SQLS);
		DataSource dataSource = getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_QUERY);

		for (Map<String, Object> map : rows) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Object val = entry.getValue();
				val = (val == null) ? "NULL" : val;
				sb.append(String.format("\t%s", val));
			}
			sb.append(EOL);
		}

		try { dataSource.getConnection().close(); }
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(sb);
		assertNotNull(sb);
	}

	// static helpers
	public static @NonNull String getSqlQuery(String sqlLabel, String pathFile) {

		String sql = "";

		Properties propertiesSql = new Properties();
		ClassLoader classLoader = DbSqliteAppTest.class.getClassLoader();
		InputStream iputStream = classLoader.getResourceAsStream(pathFile);

		try {
			propertiesSql.load(iputStream);
		} catch (IOException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		sql = propertiesSql.getProperty(sqlLabel);
		return sql;
	}

	public static StringBuilder getRows(ResultSet resultSet) {

		StringBuilder sb = new StringBuilder();
		try {
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColCount = resultSetMetaData.getColumnCount();

			// column names
			for (int ictr = 1; ictr <= intColCount; ictr++) {
				String colName = resultSetMetaData.getColumnName(ictr);
				sb.append(String.format("\t%s", colName));
			}
			sb.append(EOL);

			// row results
			while (resultSet.next()) {
				for (int jctr = 1; jctr <= intColCount; jctr++) {
					Object object = resultSet.getObject(jctr);
					object = (object == null) ? "NULL" : object;
					sb.append(String.format("\t%s", object));
				}
				sb.append(EOL);
			}
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return sb;
	}

	public static DataSource getDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USER);
		dataSource.setPassword(DB_PASS);

		return dataSource;
	}
}
