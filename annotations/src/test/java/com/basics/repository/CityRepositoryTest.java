package com.basics.repository;

import com.basics.model.City;
import jakarta.annotation.Resource;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.basics.util.UtilityMain.LOGGER;
import static com.basics.util.UtilityMain.PAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @TestPropertySource(locations = "classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityRepositoryTest {

	public static final String TAB = "\t";
	private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private static final String JDBC_SQL = "src/main/resources/import.sql";
	private static final String DB_USER = "sa"; // default is "sa"
	private static final String DB_PASSWORD = ""; // default
	private static final String DB_SQL = "SELECT * FROM cities// ";

	@Resource
	private CityRepository cityRepository;

	@BeforeAll
	public void setUp() throws Exception {
	}

	@Test
	public void testing() {
		//
		String txtLine = "testing";
		LOGGER.info(PAR + txtLine);
		assertEquals("testing", txtLine);
	}

	public void findAll() {
		//
		String txtLines = "";
		Iterable<City> iterable = cityRepository.findAll();
		List<City> cities = new ArrayList<City>();
		for (City city : iterable) {
			//
			txtLines += "\n";
			txtLines += "\t" + city.getId();
			txtLines += "\t" + city.getName();
			txtLines += "\t" + city.getPopulation();
			cities.add(city);
		}
		txtLines += PAR + cities.size();
		LOGGER.info(txtLines);
	}

	@Test
	public void testH2() {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			Class.forName("org.h2.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
			RunScript.execute(connection, new FileReader(JDBC_SQL));

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(DB_SQL);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			//
			while (resultSet.next()) {
				for (int ictr = 1; ictr <= columnCount; ictr++) {
					stringBuilder.append(resultSet.getObject(ictr)).append(TAB);
				}
				stringBuilder.append(System.lineSeparator());
			}
		} catch (SQLException | ClassNotFoundException | FileNotFoundException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		System.out.println(stringBuilder);
		assertTrue(stringBuilder.toString().length() > 10);
	}
}