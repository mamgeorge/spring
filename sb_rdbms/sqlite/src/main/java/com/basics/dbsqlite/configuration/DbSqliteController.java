package com.basics.dbsqlite.configuration; // .controller;

import com.basics.dbsqlite.persistence.Customer;
import com.basics.dbsqlite.persistence.CustomerService;

import com.basics.dbsqlite.persistence.Invoices;
import com.basics.dbsqlite.persistence.InvoicesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.OK;

// @RestController = @Controller + @ResponseBody
@RestController
public class DbSqliteController {

	private final CustomerService customerService;
	private final InvoicesService invoicesService;
	private final Random random = new Random();

	@Autowired
	DbSqliteController(CustomerService customerService, InvoicesService invoicesService) {
		this.customerService = customerService;
		this.invoicesService = invoicesService;
	}

	private static final String FRMT = "\t%02d %s %s | %s\n";
	private static final int MAX_DISPLAY = 20;

	@GetMapping( { "/", "/root", "/home", "/index" } )
	public ModelAndView root( ) {

		System.out.println("root");
		ModelAndView MAV = new ModelAndView("home", new HashMap<>());
		return MAV;
	}

	//#### REST
	@GetMapping( "/getCustomers" ) public ResponseEntity<List<Customer>> getCustomers( ) {

		List<Customer> customers = customerService.findAll();
		return new ResponseEntity<>(customers, OK);
	}

	@GetMapping( "/getCustomersRng" ) public @ResponseBody List<Customer> getCustomersRng(
		@RequestParam("beg") String beg, @RequestParam("end") String end) {

		int ibeg = Integer.parseInt(beg);
		int iend = Integer.parseInt(end);

		List<Customer> customers = customerService.findAll().subList(ibeg, iend);
		return customers;
	}

	@GetMapping( "/getCustomerRnd" ) public ResponseEntity<Customer> getCustomerRnd( ) {

		long maxId = customerService.getMaxId() + 1;
		Integer intId = random.nextInt((int) maxId) ;
		System.out.println("\nintId: " + intId);

		Customer customer = customerService.findById(intId);
		return new ResponseEntity<>(customer, OK);
	}

	@GetMapping( "/getCustomer/{idVal}" ) public ResponseEntity<Customer> getCustomer(@PathVariable int idVal ) {

		Customer customer = customerService.findById(idVal);
		return new ResponseEntity<>(customer, OK);
	}

	@GetMapping( "/getInvoices" ) public @ResponseBody List<Invoices> getInvoices( ) {

		List<Invoices> invoices = invoicesService.findAll();
		return invoices;
	}

	@GetMapping( "/getInvoiceRnd" ) public @ResponseBody Invoices getInvoiceRnd( ) {

		long maxId = invoicesService.getMaxId() + 1;
		Integer intId = random.nextInt((int) maxId) ;
		System.out.println("\nintId: " + intId);

		Invoices invoice = invoicesService.findById(intId);
		return invoice;
	}

	@GetMapping( "/getInvoice/{idVal}" ) public @ResponseBody Invoices getInvoice(@PathVariable int idVal ) {

		Invoices invoice = invoicesService.findById(idVal);
		return invoice;
	}

	//#### MVC
	@GetMapping( "/showCustomers" ) public ModelAndView showCustomers( ) {

		List<Customer> customers = customerService.findAll();
		ModelAndView modelAndView = new ModelAndView("customersList");
		modelAndView.addObject("customers", customers);
		return modelAndView;
	}

	@GetMapping( "/showCustomersMax" ) public ModelAndView showCustomersMax( ) {

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

		ModelAndView modelAndView = new ModelAndView("customersList");
		modelAndView.addObject("customers", customers);
		return modelAndView;

	}

	@GetMapping( "/showCustomerRnd" ) public ModelAndView showCustomerRnd() {

		long maxId = customerService.getMaxId() + 1;
		Integer intId = random.nextInt((int) maxId) ;
		System.out.println("\nintId: " + intId);

		Customer customer = customerService.findById(intId);
		ModelAndView modelAndView = new ModelAndView("customerOne");
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}

	@GetMapping( "/showCustomer/{idVal}" ) public ModelAndView showCustomer(@PathVariable String idVal, Model model) {

		// method called template incorrectly UNTIL TEMPLATE CSS WAS PREPENDED WITH SLASH!
		// id is normal; id.get() used with Optional
		Integer intId = Integer.parseInt(idVal);
		Customer customer = customerService.findById(intId);

		ModelAndView modelAndView = new ModelAndView("customerOne");
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}

	//#### utils
	public static String getJson(Customer customer) {

		String json = "";

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try { json = objectMapper.writeValueAsString(customer); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return json;
	}
}
