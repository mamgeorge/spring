package com.basics.dbsqlite.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.basics.dbsqlite.ReflectionHelper.LOGGER;
import static com.basics.dbsqlite.ReflectionHelper.exposeObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

	@Test void testing( ) {

		String txtLine = "testing";
		LOGGER.info(txtLine);
		assertEquals("testing", txtLine);
	}

	@Test void testCustomer( ) {

		Customer customer = new Customer();
		System.out.println(exposeObject(customer));
		Assert.notNull(customer, "Empty");
	}
}