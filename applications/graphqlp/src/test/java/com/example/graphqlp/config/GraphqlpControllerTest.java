package com.example.graphqlp.config;

import com.example.graphqlp.persistence.Actor;
import com.example.graphqlp.persistence.ActorRepository;
import com.example.graphqlp.persistence.Address;
import com.example.graphqlp.persistence.AddressRepository;
import com.example.graphqlp.persistence.City;
import com.example.graphqlp.persistence.CityRepository;
import com.example.graphqlp.persistence.Customer;
import com.example.graphqlp.persistence.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static com.example.graphqlp.GenericUtils.formatObject;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class GraphqlpControllerTest {

	public String query = "";

	@Mock private CustomerRepository customerRepository;
	@Mock private AddressRepository addressRepository;
	@Mock private CityRepository cityRepository;
	@Mock private ActorRepository actorRepository;

	private Random random = new Random();

	@InjectMocks private GraphqlpController graphqlpController;

	@BeforeEach void init() { MockitoAnnotations.initMocks(this); }

	@Test void customerByIdTest() {

		Customer customer = new Customer();
		customer.setCustomer_id(random.nextInt(20));
		customer.setCreate_date(Timestamp.from(Instant.now()));
		Optional<Customer> optional = Optional.of(customer);

		when(customerRepository.findById(anyInt())).thenReturn(optional);
		customer = graphqlpController.customerById(10);
		System.out.println(formatObject(customer));
		assertTrue(true);
	}

	@Test void addressByIdTest() {

		Address address = new Address();
		address.setAddress_id(random.nextInt(20));
		address.setLast_update(Timestamp.from(Instant.now()));
		Optional<Address> optional = Optional.of(address);

		when(addressRepository.findById(anyInt())).thenReturn(optional);
		address = graphqlpController.addressById(10);
		System.out.println(formatObject(address));
		assertTrue(true);
	}

	@Test void cityByIdTest() {

		City city = new City();
		city.setCity_id(random.nextInt(20));
		city.setLast_update(Timestamp.from(Instant.now()));
		Optional<City> optional = Optional.of(city);

		when(cityRepository.findById(anyInt())).thenReturn(optional);
		city = graphqlpController.cityById(10);
		System.out.println(formatObject(city));
		assertTrue(true);
	}

	@Test void actorByIdTest() {

		Actor actor = new Actor();
		actor.setActor_id(random.nextInt(20));
		actor.setLast_update(Timestamp.from(Instant.now()));
		Optional<Actor> optional = Optional.of(actor);

		when(actorRepository.findById(anyInt())).thenReturn(optional);
		actor = graphqlpController.actorById(10);
		System.out.println(formatObject(actor));
		assertTrue(true);
	}
}

