package com.camunda.academy;

import static com.camunda.academy.PayAppConfiguration.EOL;

public class CreditCardService {

	public String chargeCreditCard() {

		System.out.println("Charging Credit Card");
		String confirmation = String.valueOf(System.currentTimeMillis());
		return confirmation;
	}

	public String chargeCreditCard(final String reference,
		final Double amount,
		final String cardNumber,
		final String cardExpiryDate,
		final String cardCVC) {

		String txtLines = "";
		txtLines +="Starting Transaction: " + reference + EOL;
		txtLines +="Card Number: " + cardNumber + EOL;
		txtLines +="Card Expiry Date: " + cardExpiryDate+ EOL;
		txtLines +="Card CVC: " + cardCVC + EOL;
		txtLines +="Amount: " + amount + EOL;

		String confirmation = String.valueOf(System.currentTimeMillis());

		txtLines += "Successful Transaction: " + confirmation+ EOL;
		System.out.println( txtLines );

		return confirmation;
	}
}
