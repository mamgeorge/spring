package com.basics.dbsqlite.configuration; // .controller;

import com.basics.dbsqlite.model.Customer;
import com.basics.dbsqlite.model.Invoices;
import com.basics.dbsqlite.persistence.CustomerRepository;
import com.basics.dbsqlite.persistence.InvoicesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.OK;

// @RestController = @Controller + @ResponseBody
@RestController
public class ControllerCustomer {

	private CustomerRepository customerRepository;
	private final InvoicesRepository invoicesRepository;
	private final Random random = new Random();

	ControllerCustomer(CustomerRepository customerRepository, InvoicesRepository invoicesRepository) {
		this.customerRepository = customerRepository;
		this.invoicesRepository = invoicesRepository;
	}

	private static final String FRMT = "\t%02d %s %s | %s\n";
	private static final String PAGE_SORT = "customerid";
	private static final int MAX_DISPLAY = 10;
	private static final int PAGE_MIN = 0;

	@GetMapping( { "/", "/root", "/home", "/index" } )
	public ModelAndView root( ) {

		System.out.println("root");
		ModelAndView MAV = new ModelAndView("home", new HashMap<>());
		return MAV;
	}

	//#### REST
	@GetMapping( "/jsonCustomersAll" ) public ResponseEntity<List<Customer>> jsonCustomersAll( ) {

		List<Customer> customers = customerRepository.findAll();
		return new ResponseEntity<>(customers, OK);
	}

	@GetMapping( "/jsonCustomersRng" ) public @ResponseBody List<Customer> jsonCustomersRng(
		@RequestParam( "beg" ) String beg, @RequestParam( "end" ) String end) {

		int ibeg = Integer.parseInt(beg);
		int iend = Integer.parseInt(end);

		List<Customer> customers = customerRepository.findAll().subList(ibeg, iend);
		return customers;
	}

	@GetMapping( "/jsonCustomerRnd" ) public ResponseEntity<Customer> jsonCustomerRnd( ) {

		long maxId = customerRepository.count();
		Integer intId = random.nextInt((int) maxId);
		System.out.println("\nintId: " + intId);

		Customer customer = (Customer) customerRepository.findById(intId).get();
		System.out.println("\ngetCompany: " + customer.getCompany());
		System.out.println("\ncustomer: " + getJson(customer));
		return new ResponseEntity<>(customer, OK);
	}

	@GetMapping( "/jsonCustomerNum/{idVal}" )
	public ResponseEntity<Customer> jsonCustomerNum(@PathVariable int idVal) {

		Customer customer = customerRepository.findById(idVal).get();
		return new ResponseEntity<>(customer, OK);
	}

	@GetMapping( "/jsonInvoicesAll" ) public @ResponseBody List<Invoices> jsonInvoicesAll( ) {

		List<Invoices> invoices = invoicesRepository.findAll();
		return invoices;
	}

	@GetMapping( "/jsonInvoiceRnd" ) public @ResponseBody Invoices jsonInvoiceRnd( ) {

		long maxId = invoicesRepository.count() + 1;
		Integer intId = random.nextInt((int) maxId);
		System.out.println("\nintId: " + intId);

		Invoices invoice = invoicesRepository.findById(intId).get();
		return invoice;
	}

	@GetMapping( "/jsonInvoiceNum/{idVal}" ) public @ResponseBody Invoices jsonInvoiceNum(@PathVariable int idVal) {

		Invoices invoice = invoicesRepository.findById(idVal).get();
		return invoice;
	}

	//#### MVC
	@GetMapping( "/showCustomersAll" ) public ModelAndView showCustomersAll( ) {

		List<Customer> customers = customerRepository.findAll();
		ModelAndView modelAndView = new ModelAndView("customersList");
		modelAndView.addObject("customers", customers);
		return modelAndView;
	}

	@GetMapping( "/showCustomersNum/{num}" ) public ModelAndView showCustomersNum(@PathVariable String num) {

		int pageLim = Integer.parseInt(num);
		Sort sort = Sort.by(PAGE_SORT).ascending();
		Pageable pageable = (Pageable) PageRequest.of(PAGE_MIN, pageLim, sort);
		
		Page<Customer> customers = customerRepository.findAll(pageable);
		ModelAndView modelAndView = new ModelAndView("customersList");
		modelAndView.addObject("customers", customers);
		return modelAndView;
	}

	@GetMapping( "/showCustomersMax" ) public ModelAndView showCustomersMax( ) {

		StringBuilder stringBuilder = new StringBuilder();
		List<Customer> customersAll = customerRepository.findAll();
		List<Customer> customers = null;
		if ( customersAll == null || customersAll.size() < 1 ) {
			stringBuilder.append("DATA CALL FAILED OR TABLE EMPTY!");
		}
		else {
			customers = new ArrayList<Customer>(customersAll.subList(0, MAX_DISPLAY));
			customers.forEach(customer -> stringBuilder
				.append(String.format(FRMT, customer.getCustomerid(),
					customer.getFirstname(), customer.getLastname(), customer.getAddress())));
		}

		ModelAndView modelAndView = new ModelAndView("customersList");
		modelAndView.addObject("customers", customers);
		return modelAndView;

	}

	@GetMapping( "/showCustomerRnd" ) public ModelAndView showCustomerRnd( ) {

		long maxId = customerRepository.count() + 1;
		Integer intId = random.nextInt((int) maxId);
		System.out.println("\nintId: " + intId);

		Customer customer = customerRepository.getReferenceById(intId);
		ModelAndView modelAndView = new ModelAndView("customerOne");
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}

	@GetMapping( "/showCustomerNum/{idVal}" )
	public ModelAndView showCustomerNum(@PathVariable String idVal, Model model) {

		// method called template incorrectly UNTIL TEMPLATE CSS WAS PREPENDED WITH SLASH!
		// id is normal; id.get() used with Optional
		Integer intId = Integer.parseInt(idVal);
		Customer customer = customerRepository.getReferenceById(intId);

		ModelAndView modelAndView = new ModelAndView("customerOne");
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}

	@GetMapping( "/showCustomersVtl" ) public String showCustomersVtl( ) {

		String strTemplate = "src/main/resources/velocity/customersListVtl.vm";
		List<Customer> customers = customerRepository.findAll();

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("customers", customers);

		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();

		Template template = velocityEngine.getTemplate(strTemplate);

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		return stringWriter.toString();
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
