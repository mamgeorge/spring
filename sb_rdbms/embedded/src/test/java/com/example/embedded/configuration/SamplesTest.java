package com.example.embedded.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.example.embedded.configuration.DbProfile.DLM;
import static com.example.embedded.configuration.DbProfile.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SamplesTest {

	public final static String PATH_SRC_MAIN = "src/main/resources/";

	/* ScriptUtils, ResourceDatabasePopulator, RunScript

		ScriptUtils.executeSqlScript(connection, new ClassPathResource("import.sql"));
		ResourceDatabasePopulator, DataSourceInitializer
		RunScript.execute(connection, new FileReader(PATH_SRC_MAIN + "import.sql")); // h2 only
		@Sql(scripts =  {"/schema.sql", "/data.sql"}) // /src/main/resources
	*/
	@Test
	@Sql(scripts =  {"import.sql"})
	void test_h2() throws FileNotFoundException {

		String txtLines = "";
		String dbUrl = "jdbc:h2:mem:mydb;user=sa;password=;";
		String sqlDefault = "SELECT * FROM cities WHERE id > 0 ORDER BY population ASC";
		try {
			Connection connection = DriverManager.getConnection(dbUrl);
			// ScriptUtils.executeSqlScript(connection, new ClassPathResource("import.sql"));

			PreparedStatement preparedStatement = connection.prepareStatement(sqlDefault);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intColumnCount = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				for (int ictr = 1; ictr < intColumnCount + 1; ictr++) {
					txtLines += resultSet.getString(ictr) + DLM;
				}
				txtLines += EOL;
			}
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		System.out.println("txtLines: " + txtLines);
		assertNotNull(txtLines);
	}
}
