package com.example.embedded.configuration;

import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;

import javax.management.MalformedObjectNameException;
import java.util.Map;

public class Jolokia_Test {
	//
	public static void main(String[] args) {
		//
		System.out.println(jolokiaClient());
	}

	private static String jolokiaClient( ) {
		//
		String txtURL = "http://localhost:8080/jolokia";
		String pObjectName = "java.lang:type=Memory";
		String pAttribute = "HeapMemoryUsage";
		//
		// pObjectName – object name as sting which gets converted to a ObjectName
		// pAttribute – zero, one or more attributes to request.
		System.out.println("REQUIRES A JOLOKIA SERVER");
		J4pClient j4pClient = new J4pClient(txtURL);
		Map<String,Long> mapVals = null;
		try {
			J4pReadRequest l4pReadRequest = new J4pReadRequest(pObjectName, pAttribute);
			J4pReadResponse j4pReadResponse = j4pClient.execute(l4pReadRequest);
			mapVals = j4pReadResponse.getValue();
		}
		catch (MalformedObjectNameException | J4pException ex)
		{ System.out.println("ERROR: " + ex.getMessage()); }
		long longUse = mapVals.get("used");
		long longMax = mapVals.get("max");
		int intUsage = (int) (longUse * 100 / longMax);
		//
		String txtLines = "Memory usage: used: " + longUse + " / max: " + longMax + " = " + intUsage + "%";
		return txtLines;
	}
}
