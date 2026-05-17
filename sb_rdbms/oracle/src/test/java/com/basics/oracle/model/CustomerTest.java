package com.basics.oracle.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.basics.oracle.ReflectionHelper.LOGGER;
import static com.basics.oracle.ReflectionHelper.exposeObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

	@Test void testing( ) {

		String txtLine = "testing";
		LOGGER.info(txtLine);
		assertEquals("testing", txtLine);
	}

	@Test void testCustomer( ) {

		Customer_SL customer = new Customer_SL();
		System.out.println(exposeObject(customer));
		Assert.notNull(customer, "Empty");
	}
}