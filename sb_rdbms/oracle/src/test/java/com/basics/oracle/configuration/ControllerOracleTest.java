package com.basics.oracle.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import com.basics.oracle.model.Employees;

import static java.util.Optional.ofNullable;
import static com.basics.oracle.configuration.ControllerOracle.getJson;
import static com.basics.oracle.model.EmployeesTest.getEmployee;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

class ControllerOracleTest {

	private EmployeesRepository employeesRepositoryMock;
	private ControllerOracle controllerOracle;

	@BeforeEach void init( ) {

		Employees employee = getEmployee();
		List<Employees> employeeList = new ArrayList<>();
		employeeList.add(employee);

		employeesRepositoryMock = mock(EmployeesRepository.class);	
		when(employeesRepositoryMock.findAll()).thenReturn(employeeList);
		when(employeesRepositoryMock.findById(10)).thenReturn(ofNullable(employee));	
		controllerOracle = new ControllerOracle(employeesRepositoryMock);
	}

	@Test void root( ) {

		String time = controllerOracle.root();
		System.out.println(time);
		assertNotNull(time);
	}

	@Test
	void jsonCustomersAll( ) {

		ResponseEntity<List<Employees>> responseEntity = controllerOracle.jsonEmployeesAll();
		List<Employees> employeeList = responseEntity.getBody();
		System.out.println(getJson(employeeList));
		assertNotNull(employeeList);
	}

	@Test
	void jsonCustomerNum( ) {

		ResponseEntity<Employees> responseEntity = controllerOracle.jsonEmployeesNum(10);
		Employees employee = responseEntity.getBody();
		System.out.println(getJson(employee));
		assertNotNull(employee);
	}
}