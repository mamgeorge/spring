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

		Customer customer = Customer.builder()
			.customerid(10)
			.firstname("John")
			.lastname("Smith")
			.company("Huntington")
			.address("123 Oval Street")
			.city("Columbus")
			.state("Ohio")
			.country("USA")
			.postalcode("43220")
			.phone("555-1234")
			.fax("777-1234")
			.email("john.smith@example.com")	
			.supportrepid(1)
				.build();

		return customer;
	}
}