package com.basics.oracle.configuration; // .controller;

import com.basics.oracle.model.Customer_SL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.OK;

// @RestController = @Controller + @ResponseBody
@RestController
public class ControllerOracle {

	private CustomerRepository customerRepository;
	private final Random random = new Random();

	ControllerOracle(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping( { "/", "/root", "/home", "/index" } )
	public ModelAndView root( ) {

		System.out.println("root");
		ModelAndView MAV = new ModelAndView("home", new HashMap<>());
		return MAV;
	}

	//#### REST
	@GetMapping( "/jsonCustomersAll" ) public ResponseEntity<List<Customer_SL>> jsonCustomersAll( ) {

		List<Customer_SL> customers = customerRepository.findAll();
		return new ResponseEntity<>(customers, OK);
	}

	@GetMapping( "/jsonCustomersRng" ) public @ResponseBody List<Customer_SL> jsonCustomersRng(
		@RequestParam( "beg" ) String beg, @RequestParam( "end" ) String end) {

		int ibeg = Integer.parseInt(beg);
		int iend = Integer.parseInt(end);

		List<Customer_SL> customers = customerRepository.findAll().subList(ibeg, iend);
		return customers;
	}

	@GetMapping( "/jsonCustomerRnd" ) public ResponseEntity<Customer_SL> jsonCustomerRnd( ) {

		long maxId = customerRepository.count();
		Integer intId = random.nextInt((int) maxId);
		System.out.println("\nintId: " + intId);

		Customer_SL customer = (Customer_SL) customerRepository.findById(intId).get();
		System.out.println("\ngetCompany: " + customer.getCompany());
		System.out.println("\ncustomer: " + getJson(customer));
		return new ResponseEntity<>(customer, OK);
	}

	@GetMapping( "/jsonCustomerNum/{idVal}" )
	public ResponseEntity<Customer_SL> jsonCustomerNum(@PathVariable int idVal) {

		Customer_SL customer = customerRepository.findById(idVal).get();
		return new ResponseEntity<>(customer, OK);
	}

	//#### utils
	public static String getJson(Object object) {

		String json = "";

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(object); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return json;
	}
}
