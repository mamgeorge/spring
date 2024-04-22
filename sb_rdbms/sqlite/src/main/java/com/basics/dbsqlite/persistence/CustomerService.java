package com.basics.dbsqlite.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer findById(Integer id) { return customerRepository.findById(id).get(); }

	public List<Customer> findAll( ) { return customerRepository.findAll(); }

	public Customer save(Customer customer) { return customerRepository.save(customer); }

	public long getMaxId( ) { return customerRepository.count(); }
}
