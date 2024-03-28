package com.basics.dbsqlite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class CustomerService implements ICustomerService {

	@Autowired private CustomerRepository customerRepository;

	@Override public Customer findById(Integer id) { return customerRepository.findById(id).get(); }

	@Override public Customer save(Customer customer) { return customerRepository.save(customer); }

	@Override public List<Customer> findAll( ) { return (List<Customer>) customerRepository.findAll(); }
}
