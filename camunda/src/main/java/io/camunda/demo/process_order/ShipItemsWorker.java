package io.camunda.demo.process_order;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ShipItemsWorker {
	private final static Logger LOGGER = LoggerFactory.getLogger(ShipItemsWorker.class);

	@JobWorker(type = "ship-items")
	public Map<String, String> shipItems(final ActivatedJob job) {

		LOGGER.info("Processing ship-items job: {}", job.getKey());
		LOGGER.info("ship-items job completed: {}", job.getKey());
		return Map.of();
	}
}
