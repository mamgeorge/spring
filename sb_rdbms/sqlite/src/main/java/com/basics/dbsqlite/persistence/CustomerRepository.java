package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@SuppressWarnings("null")
	Page<Customer> findAll(@NonNull Pageable pageable);
 }