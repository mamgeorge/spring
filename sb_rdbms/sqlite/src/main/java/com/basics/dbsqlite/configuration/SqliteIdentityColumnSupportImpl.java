package com.basics.dbsqlite.configuration;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

// used in SqliteDialect
public class SqliteIdentityColumnSupportImpl extends IdentityColumnSupportImpl {

	@Override
	public boolean supportsIdentityColumns( ) {
		return true;
	}

	@Override
	public String getIdentitySelectString(String table, String column, int type)
		throws MappingException {
		return "select last_insert_rowid()";
	}

	@Override
	public String getIdentityColumnString(int type) throws MappingException {

		// this is duplicated in the SELECT statement
		return "integer";
	}
}