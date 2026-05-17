package com.basics.oracle.configuration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.basics.oracle.model.Customer;

//@Repository
public interface CustomerRepository { //extends JpaRepository<Customer, Integer> {

	Page<Customer> findAll(@NonNull Pageable pageable);
 }