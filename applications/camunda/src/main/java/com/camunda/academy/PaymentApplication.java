package com.camunda.academy;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientConfiguration;
import io.camunda.zeebe.client.api.response.Topology;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.camunda.academy.PayAppConfiguration.EOL;

// JobWorker
public class PaymentApplication {

	public static final Logger LOGGER = Logger.getLogger(PaymentApplication.class.getName());

	public static final String BPMN_PROCESS = "processPayment";
	public static final String JOB_TYPE_CHARGE = "chargeCreditCard";
	public static final int SECONDS = 10;

	public static void main(String[] args) {

		PaymentApplication paymentApplication = new PaymentApplication();
		String json = paymentApplication.runJobWorker();

		LOGGER.info(json);
		LOGGER.info("DONE!");
	}

	public String runJobWorker( ) {

		String json = "";
		PayAppConfiguration payAppConf = new PayAppConfiguration();
		try {
			// get Credentials
			OAuthCredentialsProvider oacProvider = payAppConf.getOacProvider();
			ZeebeClient zeebeClient =
				ZeebeClient.newClientBuilder()
					.gatewayAddress(payAppConf.getZeebeClusterIdAddressFull())
					.credentialsProvider(oacProvider)
					.build();

			// get Client
			Topology topologyRequest = zeebeClient
				.newTopologyRequest().send().join();
			json += topologyRequest.toString() + EOL;

			// get/set Customer Charge
			Map<String, Object> ccVariables = getCCVariables("", 0.0, "", "", "");

			zeebeClient.newCreateInstanceCommand()
				.bpmnProcessId(BPMN_PROCESS)
				.latestVersion()
				.variables(ccVariables)
				.send()
				.join();

			// run JobWorker CreditCardWorker
			CreditCardServiceHandler ccsHandler = new CreditCardServiceHandler();
			long longTime = Duration.ofSeconds(SECONDS).toMillis();
			JobWorker jobWorker = zeebeClient
				.newWorker()
				.jobType(JOB_TYPE_CHARGE)
				.handler(ccsHandler)
				.timeout(longTime)
				.open();

			// exploring only!
			ZeebeClientConfiguration zcConfiguration = zeebeClient.getConfiguration();
			List<String> jobWorkerTenants = zcConfiguration.getDefaultJobWorkerTenantIds();
			System.out.println("\n\n#### jobWorkerTenants: " + jobWorkerTenants);

			json += EOL + "jobWorker.isOpen(): " + jobWorker.isOpen() + EOL;
			Thread.sleep(SECONDS * 1000L);
		}
		catch (Exception ex) { LOGGER.severe("ERROR: " + ex.getMessage()); }
		return json;
	}

	public Map<String, Object> getCCVariables(String reference,
		Double amount,
		String cardNumber,
		String cardExpiry,
		String cardCVC) {

		Map<String, Object> ccVariables = new HashMap<>();

		if ( reference == null || reference.equals("") ) { reference = "C8_12345"; }
		if ( amount == null || amount == 0 ) { amount = Double.valueOf(100.00); }
		if ( cardNumber == null || cardNumber.equals("") ) { cardNumber = "1234567812345678"; }
		if ( cardExpiry == null || cardExpiry.equals("") ) { cardExpiry = "12/2024"; }
		if ( cardCVC == null || cardCVC.equals("") ) { cardCVC = "343"; }
		{
			ccVariables.put("reference", reference);
			ccVariables.put("amount", amount);
			ccVariables.put("cardNumber", cardNumber);
			ccVariables.put("cardExpiry", cardExpiry);
			ccVariables.put("cardCVC", cardCVC);
		}
		return ccVariables;
	}
}
