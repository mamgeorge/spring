package com.camunda.academy;

import java.util.logging.Logger;

import static com.camunda.academy.PayAppConfiguration.EOL;

public class CreditCardService {

	public static final Logger LOGGER = Logger.getLogger(CreditCardServiceHandler.class.getName());

	public String chargeCreditCard( ) {

		LOGGER.info("Charging Credit Card");
		String confirmation = String.valueOf(System.currentTimeMillis());
		return confirmation;
	}

	public String chargeCreditCard(final String reference,
		final Double amount,
		final String cardNumber,
		final String cardExpiryDate,
		final String cardCVC) {

		String txtLines = "";
		txtLines += "Starting Transaction: " + reference + EOL;
		txtLines += "Card Number: " + cardNumber + EOL;
		txtLines += "Card Expiry Date: " + cardExpiryDate + EOL;
		txtLines += "Card CVC: " + cardCVC + EOL;
		txtLines += "Amount: " + amount + EOL;

		String confirmation = String.valueOf(System.currentTimeMillis());

		txtLines += "Successful Transaction: " + confirmation + EOL;
		LOGGER.info(txtLines);

		return confirmation;
	}
}
