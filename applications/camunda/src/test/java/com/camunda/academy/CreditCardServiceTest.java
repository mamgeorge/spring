package com.camunda.academy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CreditCardServiceTest {

	@Test void chargeCreditCard_test( ) {

		CreditCardService creditCardService = new CreditCardService();
		String confirmation = creditCardService.chargeCreditCard();
		System.out.println("confirmation: " + confirmation);
		assertTrue(true);
	}

	@Test void chargeCreditCard_testAny( ) {

		String reference = "reference";
		Double amount = 10.00d;
		String cardNumber = "cardNumber";
		String cardExpiryDate = "cardExpiryDate";
		String cardCVC = "cardCVC";

		CreditCardService creditCardService = new CreditCardService();
		String confirmation =
			creditCardService.chargeCreditCard(reference, amount, cardNumber, cardExpiryDate, cardCVC);
		System.out.println("confirmation: " + confirmation);
		assertTrue(true);
	}
}