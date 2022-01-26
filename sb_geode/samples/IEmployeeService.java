package com.example.embedded.model;

import java.util.List;

public interface IEmployeeService {

	Employee findById(Long id);

	Employee save(Employee employee);

	List<Employee> findAll( );
}
