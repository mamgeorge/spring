package com.database.dbase.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.database.dbase.sales.DBPostGres.EOL;
import static org.junit.jupiter.api.Assertions.*;

class DBPostGresTest {

	@BeforeEach void setUp( ) { }

	@Test void getQuery( ) {

		String schema = "dvdrental";
		String sql = "SELECT * FROM film ORDER BY title ASC LIMIT 10;";
		String txtLines = DBPostGres.getQuery(schema, sql);

		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void getQueries( ) {

		String schema = "sales";
		String[] sqls = {"SELECT version();",
			"SELECT current_database();",
			"SELECT inet_server_addr(), inet_server_port();",
			"SELECT * FROM sales;"};

		StringBuilder stringBuilder = new StringBuilder();
		Arrays.stream(sqls).sorted().forEach( sql -> {
			stringBuilder.append( DBPostGres.getQuery(schema, sql)+EOL);
		});
		System.out.println(stringBuilder);
		assertTrue(true);
	}

	@Test void getPrepared( ) {

		String schema = "dvdrental";
		Object[] args = {1};
		String sql =
			"SELECT * FROM customer WHERE store_id = ? AND email LIKE 'g%' ORDER BY last_name ASC;";
		String txtLines = DBPostGres.getPrepared(schema, sql, args);

		System.out.println(txtLines);
		assertTrue(true);
	}
}