package com.basics.dbsqlite.configuration; // .controller;

import com.basics.dbsqlite.services.Customer;
import com.basics.dbsqlite.services.CustomerService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.OK;

// @RestController = @Controller + @ResponseBody
@Controller
public class DbSqliteController {

	private final CustomerService customerService;

	@Autowired DbSqliteController(CustomerService customerService) {
		this.customerService = customerService;
	}

	private static final String FRMT = "\t%02d %s %s | %s\n";
	private static final int MAX_DISPLAY = 20;

	@GetMapping( { "/", "/home", "/index" } )
	public ModelAndView home( ) {
		//
		System.out.println("home");
		ModelAndView MAV = new ModelAndView("home", new HashMap<>());
		return MAV;
	}

	// @ResponseBody or return ResponseEntity
	@GetMapping( value = "/getCustomerRnd" )
	public ResponseEntity<Customer> getCustomerRnd( ) {

		Random random = new Random();
		long longCount = customerService.getMaxId() + 1;
		Integer intId = random.nextInt((int) longCount) ;
		System.out.println("\nintId: " + intId);

		Customer customer = customerService.findById(intId);

		String json = getJson(customer);
		System.out.println("\njson: " + json);

		return new ResponseEntity<>(customer, OK);
	}

	private static String getJson(Customer customer) {

		String json = "";

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(customer); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return json;
	}

	//############

	@GetMapping( "/showCustomers" )
	public ModelAndView showCustomers( ) {

		StringBuilder stringBuilder = new StringBuilder();
		List<Customer> customersAll = customerService.findAll();
		List<Customer> customers = null;
		if ( customersAll == null || customersAll.size() < 1 ) {
			stringBuilder.append("DATA CALL FAILED OR TABLE EMPTY!");
		} else {
			customers = new ArrayList<Customer>(customersAll.subList(0, MAX_DISPLAY));
			customers.forEach(customer -> stringBuilder
				.append(String.format(FRMT, customer.getCustomerid(),
					customer.getFirstname(), customer.getLastname(), customer.getAddress())));
		}

		ModelAndView modelAndView = new ModelAndView("customers");
		modelAndView.addObject("customers", customers);
		return modelAndView;

	}

	@GetMapping( "/showCustomersAll" )
	public ModelAndView showCustomersAll( ) {

		List<Customer> customers = customerService.findAll();
		ModelAndView modelAndView = new ModelAndView("customers");
		modelAndView.addObject("customers", customers);
		return modelAndView;
	}

	// This method called the template incorrectly UNTIL THE TEMPLATE CSS WAS PREPENDED WITH A SLASH!
	@GetMapping( value = "/showCustomer/{idVal}" ) // id is normal; id.get() used with Optional
	public ModelAndView showCustomer(@PathVariable String idVal, Model model) {

		Integer intId = Integer.parseInt(idVal);
		Customer customer = customerService.findById(intId);

		ModelAndView modelAndView = new ModelAndView("customer1");
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}

}
