package com.basics.dbsqlite.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.basics.dbsqlite.ReflectionHelper.exposeObject;

class InvoicesTest {

	@Test void testInvoices( ) {

		Invoices invoices = new Invoices();
		System.out.println(exposeObject(invoices));
		Assert.notNull(invoices, "Empty");
	}
}