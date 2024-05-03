package com.example.graphqlp;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.example.graphqlp.GenericUtils.EOL;
import static com.example.graphqlp.GenericUtils.RESOURCE_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonpathSample {

	/*
		https://github.com/json-path/Jsonpath
		https://medium.com/swlh/a-jsonpath-dsl-for-transforming-json-documents-8ab1d1e21458
	*/
	private static final String jsonStorePath = RESOURCE_PATH + "store.json";
	private final String[] jsonTrans = { "transList", "transSum", "transSwitch", "transVary" };

	@Test void transform_test( ) {

		String jsonNew = "";
		String jsonStore = "";
		String jsonTransList = "";
		String jsonTransPath = RESOURCE_PATH + jsonTrans[0] + ".json";
		try {
			jsonStore = new String(Files.readAllBytes(Paths.get(jsonStorePath)));
			jsonTransList = new String(Files.readAllBytes(Paths.get(jsonTransPath)));

			// Option.CREATE_MISSING_PROPERTIES_ON_DEFINITE_PATH
			// Configuration configuration = Configuration.builder().options(option).build();
			Configuration configuration = Configuration.defaultConfiguration();
			JsonProvider jsonProvider = configuration.jsonProvider();
			Object objectJsonSource = jsonProvider.parse(jsonStore);
			DocumentContext documentContext = JsonPath.parse(objectJsonSource);
			String jsonString = documentContext.jsonString() + EOL;
			System.out.println("jsonString: " + jsonString);

			jsonNew += JsonPath.read(jsonStore, "$.store.book[*].author") + EOL;

			jsonNew += JsonPath
				.using(configuration)
				.parse(jsonStore)
				.read("$.store.book[?(@.price > 10)]", List.class);

		}
		catch (IOException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(jsonNew);
		assertTrue(true);
	}
}
