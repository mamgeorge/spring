package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.basics.dbsqlite.ReflectionHelper.exposeObject;
import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest @Disabled( "avoids loading instance" )
class CustomerServiceTest {

	@Autowired private CustomerService customerService;

	@Test
	void findById( ) {

		Customer customer = customerService.findById(10);
		System.out.println(exposeObject(customer));
		assertNotNull(customer);
	}

	@Test void findAll( ) {

		StringBuilder sb = new StringBuilder();
		Iterable<Customer> iterable = customerService.findAll();
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

		Customer customerNew = customerService.save(null);
		System.out.println("getCustomerid: " + customerNew.getCustomerid());

		long maxId = customerService.getMaxId();
		System.out.println("maxId: " + maxId);
		assertNotNull(customerNew);
	}

	@Test
	void getMaxId( ) {

		long maxId = customerService.getMaxId();
		System.out.println("maxId: " + maxId);
		assertNotNull(maxId);
	}
}