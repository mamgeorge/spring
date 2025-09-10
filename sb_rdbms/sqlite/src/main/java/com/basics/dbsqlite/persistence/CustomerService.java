package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

	public Customer save(Customer customer) {

		Customer customerNew = new Customer();
		try { customerNew = customerRepository.save(customer); }
		catch (InvalidDataAccessApiUsageException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return customerNew;
	}

	public long getMaxId( ) { return customerRepository.count(); }
}
