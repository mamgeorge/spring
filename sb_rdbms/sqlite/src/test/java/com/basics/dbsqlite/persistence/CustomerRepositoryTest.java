package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;

import static com.basics.dbsqlite.ReflectionHelper.exposeObject;
import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest @Disabled( "avoids loading instance" )
class CustomerRepositoryTest {

	@Resource private CustomerRepository customerRepository;

	@Test
	void findById( ) {

		Optional<Customer> customer = customerRepository.findById(10);
		System.out.println(exposeObject(customer.get()));
		assertNotNull(customer);
	}

	@Test void findAll( ) {

		StringBuilder sb = new StringBuilder();
		Iterable<Customer> iterable = customerRepository.findAll();
		for ( Customer customer : iterable ) {
			sb.append(customer.getCustomerid())
				.append(" / ")
				.append(customer.getFirstname() + " " + customer.getLastname())
				.append(EOL);
		}
		System.out.println(sb);
		assertNotNull(iterable);
	}

	@Test
	void save( ) {

		Customer customerNew = new Customer();
		try { customerNew = customerRepository.save(customerNew); }
		catch (InvalidDataAccessApiUsageException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println("getCustomerid: " + customerNew.getCustomerid());

		long maxId = customerRepository.count();
		System.out.println("count: " + maxId);
		assertNotNull(customerNew);
	}

	@Test
	void getCount( ) {

		long maxId = customerRepository.count();
		System.out.println("count: " + maxId);
		assertNotNull(maxId);
	}
}