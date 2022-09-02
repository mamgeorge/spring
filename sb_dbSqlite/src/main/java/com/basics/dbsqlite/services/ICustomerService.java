package com.basics.dbsqlite.services;

import java.util.List;

public interface ICustomerService {

	Customer findById(Integer id);

	Customer save(Customer customer);

	List<Customer> findAll( );
}
