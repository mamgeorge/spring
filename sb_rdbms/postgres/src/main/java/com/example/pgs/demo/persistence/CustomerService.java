package com.example.pgs.demo.persistence;

import com.example.pgs.demo.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> findAll( ) { return customerRepository.findAll(); }

	public Customer findById(Integer id) { return customerRepository.findById(id).get(); }

	public long getMaxId( ) { return customerRepository.count(); }
}
