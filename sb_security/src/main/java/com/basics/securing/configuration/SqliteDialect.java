package com.basics.securing.configuration;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

import java.sql.Types;

// because hibernate.dialect is not automatic: hibernate.dialect=com.basics.securing.configuration.DialectSqlite
public class SqliteDialect extends Dialect {

	public SqliteDialect() {
		registerColumnType(Types.BIT, "integer");
		registerColumnType(Types.TINYINT, "tinyint");
		registerColumnType(Types.SMALLINT, "smallint");
		registerColumnType(Types.INTEGER, "integer");
		registerColumnType(Types.NVARCHAR, "nvarchar");
		// other data types
	}

	@Override
	public IdentityColumnSupport getIdentityColumnSupport() {
		return new SqliteIdentityColumnSupportImpl();
	}

	@Override
	public boolean hasAlterTable() {
		return false;
	}

	@Override
	public boolean dropConstraints() {
		return false;
	}

	@Override
	public String getDropForeignKeyString() {
		return "";
	}

	@Override
	public String getAddForeignKeyConstraintString(String cn,
		String[] fk, String t, String[] pk, boolean rpk) {
		return "";
	}

	@Override
	public String getAddPrimaryKeyConstraintString(String constraintName) {
		return "";
	}
}
