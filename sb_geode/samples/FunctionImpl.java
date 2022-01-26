package com.example.embedded.configuration;

import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.stereotype.Component;

@Component
public class FunctionImpl {

	@GemfireFunction
	public void greeting(String message){
		// some logic
	}

	// ...
}