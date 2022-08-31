package com.basics.securing.services.configuration;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

import java.sql.Types;

// because hibernate.dialect is not automatic: hibernate.dialect=com.basics.securing.configuration.SqliteDialect
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

	// http://javamanikandan.blogspot.com/2014/05/sql-dialects-in-hibernate.html
	// protected MySQLStorageEngine getDefaultMySQLStorageEngine() { return InnoDBStorageEngine.INSTANCE; }

	@Override
	public IdentityColumnSupport getIdentityColumnSupport( ) { return new SqliteIdentityColumnSupportImpl(); }

	// control constraints
	@Override public boolean hasAlterTable( ) { return false; }

	@Override public boolean dropConstraints( ) { return false; }

	@Override public String getDropForeignKeyString( ) { return ""; }

	@Override public String getAddForeignKeyConstraintString(String cn, String[] fk, String t, String[] pk,
		boolean rpk) { return ""; }

	@Override public String getAddPrimaryKeyConstraintString(String constraintName) { return ""; }

	// others?
	@Override public String getCreateTableString( ) { return "create table"; }

	@Override public boolean canCreateSchema( ) { return true; }

	@Override public String[] getCreateSchemaCommand(String schemaName) {
		return new String[]{ "create schema " + schemaName };
	}

	@Override public String[] getDropSchemaCommand(String schemaName) {
		return new String[]{ "drop schema " + schemaName };
	}

	@Override public String getCurrentSchemaCommand( ) { return null; }

	@Override @Deprecated public boolean supportsUniqueConstraintInCreateAlterTable( ) { return true; }

	@Override public String getCreateTemporaryTableColumnAnnotation(int sqlTypeCode) { return ""; }

	@Override public String getTableTypeString( ) { return ""; }
}
