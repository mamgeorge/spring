package com.camunda.academy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentApplicationTest {

	@Test void runJobWorker_test( ) {

		PaymentApplication paymentApplication = new PaymentApplication();
		String json = paymentApplication.runJobWorker();
		System.out.println(json);
		assertTrue(true);
	}
}
