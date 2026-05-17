package com.basics.oracle.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.basics.oracle.ReflectionHelper.exposeObject;
import static com.basics.oracle.configuration.ControllerOracle.getJson;

import java.sql.Date;

public class EmployeesTest {

	@Test void testEmployees( ) {

		Employees employee = getEmployee();
		System.out.println(getJson(employee));
		Assert.notNull(employee, "Empty");

		String employeeExposure = exposeObject(employee);
		Assert.notNull(employeeExposure, "Empty");
	}

	public static Employees getEmployee() {
		
		Date date = Date.valueOf("2026-04-20");

		Employees employee = Employees.builder()
			.employeeId(10)
			.firstName("John")
			.lastName("Smith")
			.email("john.smith@example.com")
			.phone("555-1234")
			.hireDate(date)
			.managerId(100)
			.jobTitle("Developer")
				.build();

		return employee;
	}	
}