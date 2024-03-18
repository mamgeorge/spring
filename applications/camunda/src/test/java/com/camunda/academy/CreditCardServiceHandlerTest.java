package com.camunda.academy;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CreditCardServiceHandlerTest {

	@Mock CreditCardService creditCardService;

	@InjectMocks CreditCardServiceHandler creditCardServiceHandler;

	@BeforeEach	void setup() { MockitoAnnotations.openMocks(this); }

	@Test void handle_test( ) {

		JobClient jobClient = mock(JobClient.class);
		ActivatedJob activatedJob = mock(ActivatedJob.class);
		creditCardServiceHandler.handle(jobClient, activatedJob);
		assertTrue(true);
	}
}