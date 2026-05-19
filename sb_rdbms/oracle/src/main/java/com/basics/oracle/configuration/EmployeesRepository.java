package com.basics.oracle.configuration;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.basics.oracle.model.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

	@Query(value = "SELECT * FROM system.employees WHERE "
		+ "employee_id > 0 AND "  
		+ "hire_date > TO_DATE( :dateVal, 'YYYY-MM-DD') " 
		+ "ORDER BY last_name ASC " 
		+ "FETCH FIRST 20 ROWS ONLY", nativeQuery = true)
	List<Employees> findByDate(@Param("dateVal") String dateVal);

	Page<Employees> findAll(Pageable pageable);
 }