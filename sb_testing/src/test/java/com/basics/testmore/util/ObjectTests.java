package com.basics.testmore.util;

import com.basics.testmore.model.City;
import com.basics.testmore.model.SamplePOJO;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.meanbean.test.EqualsMethodTester;
import org.meanbean.test.HashCodeMethodTester;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Mean Bean uses Apache Commons Logging
public class ObjectTests {

	@Test void meanbean_Configuration( ) {
		//
		BeanTester beanTester = new BeanTester();
		Configuration configuration = new ConfigurationBuilder()
			.iterations(1)
			.ignoreProperty("beta")
			.ignoreProperty("gamma")
			.ignoreProperty("delta")
			.ignoreProperty("city")
			.build();
		//
		beanTester.testBean(SamplePOJO.class, configuration);
	}

	@Test void meanbean_BeanTester( ) {
		//
		System.out.println("\n" + "meanbean BeanTester: Setters & Getters");
		try {
			BeanTester beanTester = new BeanTester();
			beanTester.testBean(SamplePOJO.class);
			beanTester.testBean(City.class);
		}
		catch (Exception ex) { System.out.println("BeanTester: " + ex.getMessage()); }
	}

	@Test void meanbean_EqualsMethodTester( ) {
		//
		System.out.println("\n" + "meanbean EqualsMethodTester");
		try {
			EqualsMethodTester equalsMethodTester = new EqualsMethodTester();
			equalsMethodTester.testEqualsMethod(SamplePOJO.class);
			equalsMethodTester.testEqualsMethod(City.class);
		}
		catch (Exception ex) { System.out.println("EqualsMethodTester: " + ex.getMessage()); }
	}

	@Test void meanbean_HashCodeMethodTester( ) {
		//
		System.out.println("\n" + "meanbean HashCodeMethodTester");
		try {
			HashCodeMethodTester hashCodeMethodTester = new HashCodeMethodTester();
			hashCodeMethodTester.testHashCodeMethod(SamplePOJO.class);
			hashCodeMethodTester.testHashCodeMethod(City.class);
		}
		catch (Exception ex) { System.out.println("HashCodeMethodTester: " + ex.getMessage()); }
	}

	@Test void getField( ) {
		//
		City city = new City("Columbus", 730000);
		String txtObject = UtilityMain.getField(city, "name");
		System.out.println("getField: " + txtObject);
		assertEquals("Columbus", txtObject);
	}

	@Test void getMethod( ) { }

	@Test void exposeObject( ) { }

	@Test void putObject( ) { }
}
