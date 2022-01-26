package com.example.embedded.configuration;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region="employee")
public interface FunctionExecution {

	@FunctionId("greeting")
	public void execute(String message);

	// ...
}