package com.example.embedded.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service
public class EmployeeService implements IEmployeeService {

	@Autowired private EmployeeRepository employeeRepository;

	@Override public  Employee findById(Long id) { return employeeRepository.findById(id).get(); }

	@Override public  Employee save( Employee employee) { return employeeRepository.save(employee); }

	@Override public List< Employee> findAll() { return (List< Employee>) employeeRepository.findAll(); }
}