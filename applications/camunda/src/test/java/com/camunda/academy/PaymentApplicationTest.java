package com.camunda.academy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentApplicationTest {

	@Test @Disabled( "No need to run unintentionally!" ) void runJobWorker_test( ) {

		PaymentApplication paymentApplication = new PaymentApplication();
		String json = paymentApplication.runJobWorker();
		System.out.println(json);
		assertTrue(true);
	}
}
