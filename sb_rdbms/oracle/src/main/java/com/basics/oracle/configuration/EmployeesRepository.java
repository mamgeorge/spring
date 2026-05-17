package com.basics.oracle.configuration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.basics.oracle.model.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

	@SuppressWarnings("null")
	Page<Employees> findAll(@NonNull Pageable pageable);
 }