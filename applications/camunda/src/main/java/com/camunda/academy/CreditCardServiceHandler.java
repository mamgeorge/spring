package com.camunda.academy;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.HashMap;
import java.util.Map;

public class CreditCardServiceHandler implements JobHandler {

	private final CreditCardService creditCardService = new CreditCardService();

	@Override
	public void handle(JobClient jobClient, ActivatedJob activatedJob)  {

		Map<String, Object> variablesInput = activatedJob.getVariablesAsMap();
		String reference = (String) variablesInput.get("reference");
		Double amount = (Double) variablesInput.get("amount");
		String cardNumber = (String) variablesInput.get("cardNumber");
		String cardExpiry = (String) variablesInput.get("cardExpiry");
		String cardCVC =  (String) variablesInput.get("cardCVC");

		try {
			// creditCardService.chargeCreditCard();
			String confirmation = creditCardService.chargeCreditCard(reference, amount, cardNumber, cardExpiry, cardCVC);

			Map<String, Object> variablesOutput = new HashMap<>();
			variablesOutput.put("confirmation", confirmation);

			// jobClient.newCompleteCommand(activatedJob.getKey()).send().join();
			jobClient.newCompleteCommand(activatedJob.getKey()).variables(variablesOutput).send().join();
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }
	}
}
