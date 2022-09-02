package com.basics.dbsqlite.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository public interface CustomerRepository extends CrudRepository<Customer, Integer> { }