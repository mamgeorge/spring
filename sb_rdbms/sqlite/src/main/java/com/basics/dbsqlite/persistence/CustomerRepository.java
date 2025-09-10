package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> { }