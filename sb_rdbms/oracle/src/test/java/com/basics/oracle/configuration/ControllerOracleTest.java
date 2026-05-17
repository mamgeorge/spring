package com.basics.oracle.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.basics.oracle.model.Customer_SL;

import java.util.List;
import java.util.Objects;

import static com.basics.oracle.ReflectionHelper.exposeObject;
import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ControllerOracleTest {

	@Autowired private ControllerOracle controllerCustomer;

	@Test void root( ) {

		StringBuilder sb = new StringBuilder();
		ModelAndView modelAndView = controllerCustomer.root();
		sb.append("CONTEXT_PATH: ").append(controllerCustomer.root()).append(EOL);
		sb.append("viewName: ").append(modelAndView.getViewName()).append(EOL);

		System.out.println(sb);
		assertNotNull(controllerCustomer);
		assertTrue(Objects.requireNonNull(modelAndView.getViewName()).contains("home"));
	}

	@Test
	void jsonCustomersAll( ) {

		ResponseEntity<List<Customer_SL>> responseEntity = controllerCustomer.jsonCustomersAll();
		List<Customer_SL> customers = responseEntity.getBody();
		StringBuilder sb = new StringBuilder();
		if(customers != null){
			customers.forEach(customer -> sb.append(customer.getCustomerid()).append(" ")
				.append(customer.getFirstname()).append(" ")
				.append(customer.getLastname()).append(EOL)
			);
		}
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void jsonCustomersRng( ) {

		List<Customer_SL> customers = controllerCustomer.jsonCustomersRng("0", "5");
		StringBuilder sb = new StringBuilder();
		customers.forEach(customer -> sb.append(customer.getCustomerid()).append(" ")
			.append(customer.getFirstname()).append(" ")
			.append(customer.getLastname()).append(EOL)
		);
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void jsonCustomerRnd( ) {

		ResponseEntity<Customer_SL> responseEntity = controllerCustomer.jsonCustomerRnd();
		Customer_SL customer = responseEntity.getBody();
		System.out.println(exposeObject(Objects.requireNonNull(customer)));
		assertNotNull(customer);
	}

	@Test
	void jsonCustomerNum( ) {

		ResponseEntity<Customer_SL> responseEntity = controllerCustomer.jsonCustomerNum(10);
		Customer_SL customer = responseEntity.getBody();
		if (customer != null) {
			String txt = customer.getCustomerid() + " " +
				customer.getFirstname() + " " +
				customer.getLastname() + EOL;
			System.out.println(txt);
		}
		assertNotNull(customer);
	}

	@Test
	void getJson( ) {

		Customer_SL customer = new Customer_SL();
		customer.setFirstname("Joe");
		String json = ControllerOracle.getJson(customer);
		System.out.println(json);
		assertNotNull(json);
	}
}