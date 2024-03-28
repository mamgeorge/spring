package com.basics.dbsqlite.configuration;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

import java.sql.Types;

// because hibernate.dialect is not automatic: hibernate.dialect=com.basics.dbsqlite.configuration.SqliteDialect
public class SqliteDialect extends Dialect {

	public SqliteDialect( ) {

		registerColumnType(Types.BIT, "integer");
		registerColumnType(Types.TINYINT, "tinyint");
		registerColumnType(Types.SMALLINT, "smallint");
		registerColumnType(Types.INTEGER, "integer");
		registerColumnType(Types.NVARCHAR, "nvarchar");  // this.registerColumnType(-16, "nvarchar($l)");
		// other data types
		registerColumnType(Types.BIGINT, "bigint");
		registerColumnType(Types.FLOAT, "float");
		registerColumnType(Types.REAL, "real");
		registerColumnType(Types.DOUBLE, "double");
		registerColumnType(Types.NUMERIC, "numeric");
		registerColumnType(Types.DECIMAL, "decimal");
		registerColumnType(Types.CHAR, "char");
		registerColumnType(Types.VARCHAR, "varchar");
		registerColumnType(Types.LONGVARCHAR, "longvarchar");
		registerColumnType(Types.DATE, "date");
		registerColumnType(Types.TIME, "time");
		registerColumnType(Types.TIMESTAMP, "timestamp");
		registerColumnType(Types.BINARY, "blob");
		registerColumnType(Types.VARBINARY, "blob");
		registerColumnType(Types.LONGVARBINARY, "blob");
		registerColumnType(Types.BLOB, "blob");
		registerColumnType(Types.CLOB, "clob");
		registerColumnType(Types.BOOLEAN, "boolean");
	}

	@Override
	public IdentityColumnSupport getIdentityColumnSupport( ) { return new SqliteIdentityColumnSupportImpl(); }

	// control constraints
	@Override public boolean hasAlterTable( ) { return false; }

	@Override public boolean dropConstraints( ) { return false; }

	@Override public String getDropForeignKeyString( ) { return ""; }

	@Override public String getAddForeignKeyConstraintString(String cn, String[] fk, String t, String[] pk, boolean rpk)
	{ return ""; }

	@Override public String getAddPrimaryKeyConstraintString(String constraintName) { return ""; }
}
