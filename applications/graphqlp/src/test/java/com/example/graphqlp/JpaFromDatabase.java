package com.example.graphqlp;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.graphqlp.DbProfile.DBASE.DRB;
import static com.example.graphqlp.DbProfile.DBASE.MYSQL;
import static com.example.graphqlp.DbProfile.DBASE.ORACLE;
import static com.example.graphqlp.DbProfile.DBASE.PGS;
import static com.example.graphqlp.DbProfile.DBASE.SQLITE;
import static com.example.graphqlp.GenericUtils.EOL;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @Disabled( "integration only" )
class JpaFromDatabase {

	public static final String FRMT = "\t%-20s: %s\n";

	private final StringBuilder strb = new StringBuilder(EOL);
	private final String schemaPattern = "";
	private final String tableNamePattern = "%";
	private final String[] types = { "TABLE" };

	// hilevel
	@Test void showMetadataInfo_DRB( ) {

		String user = "";
		String pass = "";
		DbProfile dbProfile = new DbProfile(DRB, "", user, pass); // toursdb15

		String txtLines = showMetadataInfo(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void showMetadataInfo_MYSQL( ) {

		String user = System.getenv("MYSQL_USER");
		String pass = System.getenv("MYSQL_PASS");
		DbProfile dbProfile = new DbProfile(MYSQL, "", user, pass); // mydb

		String txtLines = showMetadataInfo(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void showMetadataInfo_ORACLE( ) {

		String user = System.getenv("ORACLE_USER");
		String pass = System.getenv("ORACLE_PASS");
		DbProfile dbProfile = new DbProfile(ORACLE, "", user, pass); // ANONYMOUS

		String txtLines = showMetadataInfo(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void showMetadataInfo_PGS( ) {

		String user = System.getenv("POSTGRES_USER");
		String pass = System.getenv("POSTGRES_PASS");
		DbProfile dbProfile = new DbProfile(PGS, "", user, pass); // dvdrental

		String txtLines = showMetadataInfo(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void showMetadataInfo_SQLITE( ) {

		String user = "";
		String pass = "";
		DbProfile dbProfile = new DbProfile(SQLITE, "", user, pass); // APP

		String txtLines = showMetadataInfo(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void buildTableList_fromRS_PGS( ) {

		String user = System.getenv("POSTGRES_USER");
		String pass = System.getenv("POSTGRES_PASS");
		DbProfile dbProfile = new DbProfile(PGS, "", user, pass); // dvdrental

		String catalog = "dvdrental";
		String schema = "public";
		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet resultSet = dbMetaData.getTables(catalog, schema, tableNamePattern, types);

			getResultSetInfo("rsTables", resultSet);
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(strb);
		assertTrue(true);
	}

	public String showMetadataInfo(DbProfile dbProfile) {

		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			DatabaseMetaData dbMetaData = connection.getMetaData();

			// extract cool info!
			showDbMetadataInfo(dbMetaData);
			String catalog = getResultSetInfo("1 rsCatalogs", dbMetaData.getCatalogs());
			String schema = getResultSetInfo("2 rsSchemas", dbMetaData.getSchemas(catalog, schemaPattern));
			getResultSetInfo("3 rsTableTypes", dbMetaData.getTableTypes());
			getResultSetInfo("4 rsTableInfo", dbMetaData.getTypeInfo());
			getResultSetInfo("5 rsTables", dbMetaData.getTables(catalog, schema, tableNamePattern, types));
		}
		catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		return strb.toString();
	}

	private void showDbMetadataInfo(DatabaseMetaData dbmd) {

		try {
			strb.append(String.format(FRMT, "getDbProductName..", dbmd.getDatabaseProductName()));
			strb.append(String.format(FRMT, "getDbProductVersion", dbmd.getDatabaseProductVersion()));
			strb.append(String.format(FRMT, "getDriverName.....", dbmd.getDriverName()));
			strb.append(String.format(FRMT, "getDriverVersion..", dbmd.getDriverVersion()));
			strb.append(String.format(FRMT, "getSchemaTerm.....", dbmd.getSchemaTerm()));
			strb.append(String.format(FRMT, "getSystemFunctions", dbmd.getSystemFunctions()));
			strb.append(String.format(FRMT, "getUserName.......", dbmd.getUserName()));
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		strb.append(EOL);
	}

	private String getResultSetInfo(String label, ResultSet resultSet) {

		String txtLine = "";
		boolean boolFrmt = label.contains("rsTableInfo") || label.contains("rsTables");
		boolean ifResultSetFull = false;
		try {
			while ( resultSet.next() ) {

				int ictr = resultSet.getMetaData().getColumnCount();

				// loop all columns
				for ( int qctr = 1; qctr <= ictr; qctr++ ) {

					txtLine = resultSet.getString(qctr);
					if ( txtLine != null && !txtLine.isEmpty() ) {

						// for rsTableInfo & rsTables format differently
						if ( boolFrmt ) {
							if ( boolFrmt && qctr == 1 ) {
								// pad out the 1st item; only occurs with rsTableInfo
								strb.append(String.format("\t4 %-20s ", txtLine));
							} else {
								strb.append("\t " + txtLine + " ");
							}
						} else { strb.append(String.format(FRMT, label, txtLine)); }
					}
				}
				if ( boolFrmt ) { strb.append(EOL); }
				ifResultSetFull = true;
			}
			if ( !ifResultSetFull ) { strb.append(String.format(FRMT, label, "EMPTY RESULTSET!")); }
			strb.append(EOL);
		}
		catch (SQLException ex) { System.out.println("ERROR: " + label + " / " + ex.getMessage()); }
		return txtLine;
	}

	// weediness
	@Test void buildTableColumnList_DRB( ) {

		String user = "";
		String pass = "";
		DbProfile dbProfile = new DbProfile(DRB, "", user, pass); // dvdrental

		String catalog = "";
		String schema = "APP";

		buildTableList(dbProfile, catalog, schema);
		System.out.println(strb);
		assertTrue(true);
	}

	@Test void buildTableColumnList_MYSQL( ) {

		String user = System.getenv("MYSQL_USER");
		String pass = System.getenv("MYSQL_PASS");
		DbProfile dbProfile = new DbProfile(MYSQL, "", user, pass); // mydb

		String catalog = "mydb";
		String schema = "";

		buildTableList(dbProfile, catalog, schema);
		System.out.println(strb);
		assertTrue(true);
	}

	@Test void buildTableColumnList_ORACLE( ) {

		String user = System.getenv("ORACLE_USER");
		String pass = System.getenv("ORACLE_PASS");
		DbProfile dbProfile = new DbProfile(ORACLE, "", user, pass); // ANONYMOUS

		String catalog = "";
		String schema = "";

		buildTableList(dbProfile, catalog, schema);
		System.out.println(strb);
		assertTrue(true);
	}

	@Test void buildTableColumnList_PGS( ) {

		String user = System.getenv("POSTGRES_USER");
		String pass = System.getenv("POSTGRES_PASS");
		DbProfile dbProfile = new DbProfile(PGS, "", user, pass); // dvdrental

		String catalog = "dvdrental";
		String schema = "public";

		buildTableList(dbProfile, catalog, schema);
		System.out.println(strb);
		assertTrue(true);
	}

	@Test void buildTableColumnList_SQLITE( ) {

		String user = "";
		String pass = "";
		DbProfile dbProfile = new DbProfile(SQLITE, "", user, pass); // dvdrental

		String catalog = "";
		String schema = "";

		buildTableList(dbProfile, catalog, schema);
		System.out.println(strb);
		assertTrue(true);
	}

	private void buildTableList(DbProfile dbProfile, String catalog, String schema) {

		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet resultSetTab = dbMetaData.getTables(catalog, schema, tableNamePattern, types);

			int tctr = 0;
			while ( resultSetTab.next() ) {

				int ictr = resultSetTab.getMetaData().getColumnCount();
				for ( int qctr = 1; qctr <= ictr; qctr++ ) {

					String tabcolnam = resultSetTab.getMetaData().getColumnName(qctr);
					String tabcolval = resultSetTab.getString(qctr);
					if ( tabcolval != null && !tabcolval.isEmpty() ) {

						if ( tabcolnam.equals("TABLE_NAME") ) {
							strb.append(String.format("\t%02d %s\n", ++tctr, tabcolval));
							buildColumnList(dbMetaData, catalog, schema, tabcolval);
						}
					}
				}
				strb.append(EOL);
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}

	private void buildColumnList(DatabaseMetaData DMD, String catalog, String schema, String tabcolval) {

		try {
			ResultSet resultSetCol = DMD.getColumns(catalog, schema, tabcolval, "%");
			while ( resultSetCol.next() ) {
				int cctr = resultSetCol.getMetaData().getColumnCount();
				for ( int rctr = 1; rctr <= cctr; rctr++ ) {

					String colcolnam = resultSetCol.getMetaData().getColumnName(rctr);
					String colcolval = resultSetCol.getString(rctr);
					if ( colcolval != null && !colcolval.isEmpty() ) {

						// COLUMN_NAME, DATA_TYPE, TYPE_NAME, COLUMN_SIZE
						if ( colcolnam.equals("COLUMN_NAME") ) {
							strb.append(String.format("\t\t%-20s", colcolval));
						}
						if ( colcolnam.equals("DATA_TYPE") ) {
							strb.append(String.format("%-4d ", Integer.parseInt(colcolval)));
						}
						if ( colcolnam.equals("TYPE_NAME") ) {
							strb.append(String.format("%-10s", colcolval));
						}
						if ( colcolnam.equals("COLUMN_SIZE") ) {
							strb.append(String.format("%02d", Integer.parseInt(colcolval)));
						}
					}
				}
				strb.append("\n");
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}
}
