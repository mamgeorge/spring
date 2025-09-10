package com.basics.dbsqlite.configuration;

import com.basics.dbsqlite.model.Customer;
import com.basics.dbsqlite.model.Invoices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

import static com.basics.dbsqlite.ReflectionHelper.exposeObject;
import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ControllerCustomerTest {

	@Autowired private ControllerCustomer controllerCustomer;

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
	void getCustomers( ) {

		ResponseEntity<List<Customer>> responseEntity = controllerCustomer.getCustomers();
		List<Customer> customers = responseEntity.getBody();
		StringBuilder sb = new StringBuilder();
		customers.forEach(customer -> sb.append(customer.getCustomerid()).append(" ")
			.append(customer.getFirstname()).append(" ")
			.append(customer.getLastname()).append(EOL)
		);
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void getCustomersRng( ) {

		List<Customer> customers = controllerCustomer.getCustomersRng("0", "5");
		StringBuilder sb = new StringBuilder();
		customers.forEach(customer -> sb.append(customer.getCustomerid()).append(" ")
			.append(customer.getFirstname()).append(" ")
			.append(customer.getLastname()).append(EOL)
		);
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void getCustomerRnd( ) {

		ResponseEntity<Customer> responseEntity = controllerCustomer.getCustomerRnd();
		Customer customer = responseEntity.getBody();
		System.out.println(exposeObject(Objects.requireNonNull(customer)));
		assertNotNull(customer);
	}

	@Test
	void getCustomer( ) {

		ResponseEntity<Customer> responseEntity = controllerCustomer.getCustomer(10);
		Customer customer = responseEntity.getBody();
		String txt = customer.getCustomerid() + " " +
			customer.getFirstname() + " " +
			customer.getLastname() + EOL;
		System.out.println(txt);
		assertNotNull(customer);
	}

	// Invoices
	@Test
	void getInvoices( ) {

		List<Invoices> invoices = controllerCustomer.getInvoices();
		StringBuilder sb = new StringBuilder();
		invoices.forEach(customer -> sb.append(customer.getInvoiceid()).append(" ")
			.append(customer.getInvoicedate()).append(" ")
			.append(customer.getBillingaddress()).append(EOL)
		);
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void getInvoiceRnd( ) {

		Invoices invoices = controllerCustomer.getInvoiceRnd();
		System.out.println(exposeObject(invoices));
		assertNotNull(invoices);
	}

	@Test
	void getInvoice( ) {

		Invoices invoices = controllerCustomer.getInvoice(10);
		String txt = invoices.getInvoiceid() + " " +
			invoices.getInvoicedate() + " " +
			invoices.getBillingaddress() + EOL;
		System.out.println(txt);
		assertNotNull(invoices);
	}

	// display
	@Test
	void showCustomers( ) {

		ModelAndView MAV = controllerCustomer.showCustomers();
		String name = MAV.getViewName();
		ModelMap modelMap = MAV.getModelMap();
		List<Customer> customers = (List<Customer>) modelMap.get("customers");

		StringBuilder sb = new StringBuilder();
		sb.append("name: ").append(name).append(EOL);
		sb.append("len: ").append(customers.size()).append(EOL);
		for ( Customer customer : customers ) {
			sb.append(customer.getCustomerid())
				.append(": ")
				.append(customer.getFirstname())
				.append(" ")
				.append(customer.getLastname())
				.append(EOL);
		}
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void showCustomersMax( ) {

		ModelAndView MAV = controllerCustomer.showCustomersMax();
		String name = MAV.getViewName();
		ModelMap modelMap = MAV.getModelMap();
		List<Customer> customers = (List<Customer>) modelMap.get("customers");

		StringBuilder sb = new StringBuilder();
		sb.append("name: ").append(name).append(EOL);
		sb.append("len: ").append(modelMap.size()).append(EOL);
		for ( Customer customer : customers ) {
			sb.append(customer.getCustomerid())
				.append(": ")
				.append(customer.getFirstname())
				.append(" ")
				.append(customer.getLastname())
				.append(EOL);
		}
		System.out.println(sb);
		assertNotNull(sb);
	}

	@Test
	void showCustomerRnd( ) {

		ModelAndView MAV = controllerCustomer.showCustomerRnd();
		String name = MAV.getViewName();
		ModelMap modelMap = MAV.getModelMap();
		Customer customer = (Customer) modelMap.get("customer");
		String txt = customer.getCustomerid() + " " +
			customer.getFirstname() + " " +
			customer.getLastname() + EOL;
		System.out.println(txt);
		assertNotNull(customer);
	}

	@Test
	void showCustomer( ) {

		Model model = null;
		ModelAndView MAV = controllerCustomer.showCustomer("10", model);
		String name = MAV.getViewName();
		ModelMap modelMap = MAV.getModelMap();
		Customer customer = (Customer) modelMap.get("customer");
		String txt = customer.getCustomerid() + " " +
			customer.getFirstname() + " " +
			customer.getLastname() + EOL;
		System.out.println(txt);
		assertNotNull(customer);
	}

	@Test
	void getJson( ) {

		Customer customer = new Customer();
		customer.setFirstname("Joe");
		String json = ControllerCustomer.getJson(customer);
		System.out.println(json);
		assertNotNull(json);
	}
}