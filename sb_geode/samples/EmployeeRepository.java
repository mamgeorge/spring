package com.example.embedded.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Employee findByName(String name);

	Iterable<Employee> findBySalaryGreaterThan(double salary);

	Iterable<Employee> findBySalaryLessThan(double salary);

	Iterable<Employee> findBySalaryGreaterThanAndSalaryLessThan(double salary1, double salary2);
}