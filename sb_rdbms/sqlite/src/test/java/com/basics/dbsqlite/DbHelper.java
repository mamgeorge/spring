package com.basics.dbsqlite;

import lombok.Data;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.basics.dbsqlite.model.Customer;

import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// @SpringBootTest(classes = DbHelper.class)
public class DbHelper {

	enum SQL_QUERIES {
		SQL_CUSTOMERS, SQL_EMPLOYEES, SQL_INVOICES, SQL_PLAYLISTS
	};

	public static final String FRMT = "\t%-20s [%s]\n";
	public static final String DB_DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:C:/workspace/dbase/sqlite/chinook.db";
	public static final String DB_USER = "";
	public static final String DB_PASS = "";
	public static final String PATH_SQLS = "samples.sql";

	@Test void read_driverManager() { 
		
		// uses getSqlQuery
		String sqlLabel = SQL_QUERIES.SQL_CUSTOMERS.name(); 
		String SQL_QUERY = getSqlQuery(sqlLabel, PATH_SQLS);
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

		// BeanPropertyRowMapper
		String SQL_QUERY = getSqlQuery(SQL_QUERIES.SQL_CUSTOMERS.name(), PATH_SQLS);
		DataSource dataSource = getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		BeanPropertyRowMapper<Customer> BPRM = new BeanPropertyRowMapper<>(Customer.class);
		List<Customer> customers = jdbcTemplate.query(SQL_QUERY, BPRM);

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
		
		// functional programming
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

	@Test void test_getCustomerMinis() {

		String json = getCustomerMinis("");
		System.out.println("json: " + json);		
		assertNotNull(json);
	}

	// helpers
	public static String getCustomerMinis(String sql) {

		String json = "";
		if(sql == null || sql.isEmpty()) 
		{ sql = getSqlQuery(SQL_QUERIES.SQL_CUSTOMERS.name(), PATH_SQLS); }
	 
		DataSource dataSource = getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		BeanPropertyRowMapper<CustomerMini> BPRM = new BeanPropertyRowMapper<>(CustomerMini.class);
		List<CustomerMini> customers = jdbcTemplate.query(sql, BPRM);

		try { dataSource.getConnection().close(); }
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	
		json = getJson(customers);
		return json;
	}

	public static @NonNull String getSqlQuery(String sqlLabel, String pathFile) {

		String sql = "";

		Properties propertiesSql = new Properties();
		ClassLoader classLoader = DbHelper.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(pathFile);

		try { propertiesSql.load(inputStream); } 
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage());}
		
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

	public static String getJson(Object object) {

		String json = "";

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(object); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return json;
	}
}

@Data class CustomerMini {
	
	private Integer customerid; // schema = "main"
	private String firstname;
	private String lastname;
	private String company;
	private String address;
}