package com.basics.securing.configuration; // .controller;

import com.basics.securing.services.Customer;
import com.basics.securing.services.ICustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// @RestController = @Controller + @ResponseBody
@Controller
public class SecuringWebController {
	//
	@Autowired private ICustomerService customerService;
	@Autowired private ApplicationContext applicationContext; //added for close

	private static final String FRMT = "\t%02d %s %s | %s\n";
	private static final String RETURN = "<br /><a href = '/home'>return</a>";
	private static final int MAX_DISPLAY = 20;

	@GetMapping( { "/roots" } )
	public String roots( ) {
		//
		System.out.println("root");
		return Instant.now() + RETURN;
	}

	@GetMapping( { "/", "/home", "/index" } )
	public ModelAndView home( ) {
		//
		System.out.println("home");
		ModelAndView MAV = new ModelAndView("home", new HashMap<>());
		return MAV;
	}

	@GetMapping( "/showCustomers" )
	public ModelAndView showCustomers( ) {

		System.out.println("showCustomers");
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
		System.out.println(stringBuilder.toString().substring(0,40));
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("customers", customers);
		modelAndView.setViewName("customers");
		return modelAndView;

	}

	@GetMapping( value = "/showCustomerPath/{idVal}"  ) // id is normal; id.get() used with Optional
	public ModelAndView showCustomerPath(@PathVariable String idVal, Model model) {

		System.out.println("showCustomerPath/{" + idVal + "}");
		Integer intId = Integer.parseInt(idVal);
		Customer customer = customerService.findById(intId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("customer", customer);
		modelAndView.setViewName("customer1");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping( value = "/showCustomer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public String showCustomer(@RequestParam( name = "idVal", defaultValue = "5" ) String customerId) {

		System.out.println("showCustomer: " + customerId);
		Integer intId = Integer.parseInt(customerId);
		Customer customer = customerService.findById(intId);
		//
		String txtLines = "";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // writerWithDefaultPrettyPrinter
		try { txtLines = objectMapper.writeValueAsString(customer); }
		catch (JsonProcessingException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(txtLines);

		return txtLines;
	}
}
