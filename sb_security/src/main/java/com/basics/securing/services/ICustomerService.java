package com.basics.securing.services;

import java.util.List;

public interface ICustomerService {

	Customer findById(int id);

	Customer save(Customer customer);

	List<Customer> findAll();
}
