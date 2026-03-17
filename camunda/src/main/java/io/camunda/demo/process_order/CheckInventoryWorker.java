package io.camunda.demo.process_order;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import io.camunda.client.api.response.ActivatedJob;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CheckInventoryWorker {

	private final static Logger LOGGER = LoggerFactory.getLogger(CheckInventoryWorker.class);

	@JobWorker(type = "check-inventory")
	public Map<String, String> checkInventory(final ActivatedJob job,
		@Variable(name = "item")
		@Nullable String itemOrdered) {

		String item;
		if (itemOrdered == null || itemOrdered.isEmpty())
		{ item = "default-item"; } else
		{ item = itemOrdered; }

		LOGGER.info("Checking inventory for item: {}", job.getKey());
		LOGGER.info("check-inventory completed ");
		return Map.of("item", item + " allocated");
	}
}
