package com.basics.dbsqlite.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> { }