package com.example.graphqlp;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.graphqlp.DbProfile.DBASE.DRB;
import static com.example.graphqlp.DbProfile.DBASE.PGS;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @Disabled( "integration only" )
public class JpaFromDatabase {

	public static final String EOL = "\n";
	public static final String FRMT = "\t%-20s: %s\n";

	private final StringBuilder strb = new StringBuilder(EOL);
	private final String schemaPattern = "";
	private final String tableNamePattern = "%";
	private final String[] types = { "TABLE" };

	@Test void buildClassesfromSchema_DRB( ) {

		DbProfile dbProfile = new DbProfile(DRB, "", "", ""); // APP

		String txtLines = buildClassesfromSchema(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void buildClassesfromSchema_PGS( ) {

		String user = System.getenv("POSTGRES_USER");
		String pass = System.getenv("POSTGRES_PASS");
		DbProfile dbProfile = new DbProfile(PGS, "", user, pass); // dvdrental

		String txtLines = buildClassesfromSchema(dbProfile);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test  void buildTableListGeneric_PGS( ) {

		String user = System.getenv("POSTGRES_USER");
		String pass = System.getenv("POSTGRES_PASS");
		DbProfile dbProfile = new DbProfile(PGS, "", user, pass); // dvdrental

		String catalog = "dvdrental";
		String schema = "public";
		ResultSet resultSet = null;
		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			DatabaseMetaData dbMetaData = connection.getMetaData();
			resultSet = dbMetaData.getTables(catalog, schema, tableNamePattern, types);
			getResultSetInfo("rsTables", resultSet);
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(strb);
		assertTrue(true);
	}

	@Test void buildTableList_PGS( ) {

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

			while ( resultSet.next() ) {

				int ictr = resultSet.getMetaData().getColumnCount();
				for ( int qctr = 1; qctr <= ictr; qctr++ ) {

					String txtLine = resultSet.getString(qctr);
					if ( txtLine != null && !txtLine.isEmpty() ) { strb.append("\t" + txtLine + " "); }
				}
				strb.append(EOL);
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(strb);
		assertTrue(true);
	}

	public String buildClassesfromSchema(DbProfile dbProfile) {

		try {
			DriverManager.registerDriver(dbProfile.getDriver());
			Connection connection = DriverManager.getConnection(dbProfile.getDburl(), dbProfile.getProps());
			DatabaseMetaData dbMetaData = connection.getMetaData();

			// extract cool info!
			getDbMetaData(dbMetaData);
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

	private void getDbMetaData(DatabaseMetaData dbmd) {

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
			}
			strb.append(EOL);
		}
		catch (SQLException ex) { System.out.println("ERROR: " + label + " / " + ex.getMessage()); }
		return txtLine;
	}
}
