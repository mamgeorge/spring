package com.basics.oracle.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.basics.oracle.model.Employees;
import java.util.List;
import java.util.ArrayList;

import static java.util.Optional.ofNullable;
import static com.basics.oracle.configuration.ControllerOracle.getJson;
import static com.basics.oracle.configuration.ControllerOracle.PAGE_SORT;
import static com.basics.oracle.configuration.ControllerOracle.PAGE_MIN;
import static com.basics.oracle.model.EmployeesTest.getEmployee;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerOracleTest {

	private EmployeesRepository employeesRepositoryMock;
	private ControllerOracle controllerOracle;

	@SuppressWarnings({ "null" })
	@BeforeEach void init( ) {

		Employees employee = getEmployee();
		List<Employees> employeeList = new ArrayList<>();
		employeeList.add(employee);
	
		Sort sort = Sort.by(PAGE_SORT).ascending();
		Pageable pageable = (Pageable) PageRequest.of(PAGE_MIN, 1, sort);
		Page<Employees> employeesPage = new PageImpl<>(employeeList, pageable, employeeList.size());

		employeesRepositoryMock = mock(EmployeesRepository.class);	
		when(employeesRepositoryMock.findAll()).thenReturn(employeeList);
		when(employeesRepositoryMock.findAll(any(Pageable.class))).thenReturn(employeesPage);
		when(employeesRepositoryMock.findById(10)).thenReturn(ofNullable(employee));	
		controllerOracle = new ControllerOracle(employeesRepositoryMock);
	}

	@Test void root( ) {

		String time = controllerOracle.root();
		System.out.println(time);
		assertNotNull(time);
	}

	@Test void home() {
		
		ModelAndView MAV = controllerOracle.home();
		String viewName= MAV.getViewName();
		ModelMap modelMap = MAV.getModelMap();
		System.out.println("viewName: " + viewName);
		System.out.println("modelMap: " + getJson(modelMap)	);
		assertNotNull(viewName);
		assertNotNull(modelMap);
	}


	@Test void jsonCustomersAll( ) {

		ResponseEntity<List<Employees>> responseEntity = controllerOracle.jsonEmployeesAll();
		List<Employees> employeeList = responseEntity.getBody();
		System.out.println(getJson(employeeList));
		assertNotNull(employeeList);
	}

	@Test void jsonEmployeesRng( ) {

		ResponseEntity<List<Employees>> responseEntity = controllerOracle.jsonEmployeesRng();
		List<Employees> employeeList = responseEntity.getBody();
		System.out.println(getJson(employeeList));
		assertNotNull(employeeList);
	}

	@Test void jsonCustomerNum( ) {

		ResponseEntity<Employees> responseEntity = controllerOracle.jsonEmployeesNum(10);
		Employees employee = responseEntity.getBody();
		System.out.println(getJson(employee));
		assertNotNull(employee);
	}
}