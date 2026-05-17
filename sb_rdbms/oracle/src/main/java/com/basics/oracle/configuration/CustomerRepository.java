package com.basics.oracle.configuration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.basics.oracle.model.Customer_SL;

@Repository
public interface CustomerRepository extends JpaRepository<Customer_SL, Integer> {

	@SuppressWarnings("null")
	Page<Customer_SL> findAll(@NonNull Pageable pageable);
 }