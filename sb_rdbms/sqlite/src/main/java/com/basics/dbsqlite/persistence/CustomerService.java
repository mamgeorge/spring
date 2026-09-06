package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;

import lombok.NonNull;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer findById(Integer id) { 
		@NonNull Integer idSafe = (id != null) ? id : 0; 
		return customerRepository.findById(idSafe).get(); }

	public List<Customer> findAll( ) { return customerRepository.findAll(); }

	public Customer save(Customer customer) {

		Customer customerNew = new Customer();
		@NonNull Customer customerSafe = (customer != null) ? customer : customerNew; 
		try { customerNew = customerRepository.save(customerSafe); }
		catch (InvalidDataAccessApiUsageException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return customerNew;
	}

	public long getMaxId( ) { return customerRepository.count(); }
}
