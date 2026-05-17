package com.basics.oracle.configuration; // .controller;

import com.basics.oracle.model.Employees;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.List;
import static org.springframework.http.HttpStatus.OK;

// @RestController = @Controller + @ResponseBody
@RestController
public class ControllerOracle {

	private EmployeesRepository employeesRepository;
	private static final String PAGE_SORT = "lastName";
	private static final int PAGE_MAX = 5;
	private static final int PAGE_MIN = 0;

	ControllerOracle(EmployeesRepository employeesRepository) {
		this.employeesRepository = employeesRepository;
	}

	@GetMapping( { "/", "/root", "/home", "/time", "/index" } )
	public String root( ) {

		System.out.println("root");
		String time = Instant.now().toString();
		return time;
	}

	//#### REST
	@GetMapping( "/jsonEmployeesAll" ) public ResponseEntity<List<Employees>> jsonEmployeesAll( ) {

		List<Employees> employees = employeesRepository.findAll();
		System.out.println("jsonEmployeesAll: " + employees.size());
		System.out.println(getJson(employees));		
		return new ResponseEntity<>(employees, OK);
	}

	@GetMapping( "/jsonEmployeesRng" ) public ResponseEntity<List<Employees>> jsonEmployeesRng( ) {

		Sort sort = Sort.by(PAGE_SORT).ascending();
		Pageable pageable = (Pageable) PageRequest.of(PAGE_MIN, PAGE_MAX, sort);
		Page<Employees> employeesPage = employeesRepository.findAll(pageable);
		List<Employees> employees = employeesPage.toList();

		System.out.println("jsonEmployeesRng: " + employees.size());
		System.out.println(getJson(employees.get(0)));		
		return new ResponseEntity<>(employees, OK);
	}

	@GetMapping( "/jsonEmployeesNum/{idVal}" )
	public ResponseEntity<Employees> jsonEmployeesNum(@PathVariable int idVal) {

		Employees employee = employeesRepository.findById(idVal).get();
		System.out.println(getJson(employee));	
		return new ResponseEntity<>(employee, OK);
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
