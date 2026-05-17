package com.basics.oracle.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.basics.oracle.ReflectionHelper.exposeObject;
import static com.basics.oracle.configuration.ControllerOracle.getJson;

class CustomerTest {

	@Test void testCustomer( ) {

		Customer customer = getCustomer();
		System.out.println(getJson(customer));
		Assert.notNull(customer, "Empty");

		String customerExposure = exposeObject(customer);
		Assert.notNull(customerExposure, "Empty");
	}

	public static Customer getCustomer( ) {

		return new Customer( 10, "John", "Smith", "Huntington", 
		"123 Oval Street","Columbus","Ohio","USA", "43220", 
		"555-1234", "777-1234", "john.smith@example.com" ,1);
	}
}