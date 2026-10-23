package com.basics.dbsqlite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DbSqliteAppTest {

	@Test void contextLoads() {	

		DbSqliteApp dbSqliteApp = new DbSqliteApp();
		assertNotNull(dbSqliteApp);
	}
}
