package com.example.graphqlp.configuration;

import com.example.graphqlp.persistence.Actor;
import com.example.graphqlp.persistence.ActorRepository;
import com.example.graphqlp.persistence.Address;
import com.example.graphqlp.persistence.AddressRepository;
import com.example.graphqlp.persistence.City;
import com.example.graphqlp.persistence.CityRepository;
import com.example.graphqlp.persistence.Customer;
import com.example.graphqlp.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.RESET_CONTENT;

@RestController
public class GraphqlpController {

	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;
	private final ActorRepository actorRepository;
	private final CityRepository cityRepository;
	private final Random random = new Random();

	@Autowired public GraphqlpController(CustomerRepository customerRepository,
		AddressRepository addressRepository, CityRepository cityRepository,
		ActorRepository actorRepository) {

		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
		this.cityRepository = cityRepository;
		this.actorRepository = actorRepository;
	}

	@GetMapping( { "/", "/home", "/root" } )
	public ModelAndView root( ) {

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		return new ModelAndView("index", "timer", timer);
	}

	//#### graphql ####
	@QueryMapping public List<Customer> getCustomersRng(@Argument int beg, @Argument int end) { return customerRepository.findAll().subList(beg, end); }
	@QueryMapping public List<Address> getAddressesRng(@Argument int beg, @Argument int end) { return addressRepository.findAll().subList(beg, end); }
	@QueryMapping public List<City> getCitiesRng(@Argument int beg, @Argument int end) { return cityRepository.findAll().subList(beg, end); }
	@QueryMapping public List<Actor> getActorsRng(@Argument int beg, @Argument int end) { return actorRepository.findAll().subList(beg, end); }

	@QueryMapping public Customer customerById(@Argument int customer_id) { return customerRepository.findById(customer_id).get(); }
	@QueryMapping public Address addressById(@Argument int address_id) { return addressRepository.findById(address_id).get(); }
	@QueryMapping public City cityById(@Argument int city_id) { return cityRepository.findById(city_id).get(); }
	@QueryMapping public Actor actorById(@Argument int actor_id) { return actorRepository.findById(actor_id).get(); }

	@SchemaMapping public Address address(Customer customer) { return addressRepository.findById(customer.getAddress_id()).get(); }
	@SchemaMapping public City city(Address address) { return cityRepository.findById(address.getCity_id()).get(); }

	@MutationMapping public Actor addActorInf(@Argument String first_name, @Argument String last_name) {

		Actor actorNew = new Actor();
		actorNew.setFirst_name(first_name);
		actorNew.setLast_name(last_name);
		actorNew.setLast_update(Timestamp.from(Instant.now()));

		actorNew = actorRepository.save(actorNew);
		int actor_id = actorNew.getActor_id();
		System.out.println("actor_id: " + actor_id);
		return actorRepository.findById(actor_id).get();
	}
	@MutationMapping public Actor addActorObj(@Argument Actor actor) {

		actor.setLast_update(Timestamp.from(Instant.now()));
		Actor actorNew = actorRepository.save(actor);
		int actor_id = actorNew.getActor_id();
		System.out.println("actor_id: " + actor_id);
		return actorRepository.findById(actor_id).get();
	}

	//#### regular REST #### { return addressRepository.findById(address_id); }
	@GetMapping( "/getCustomers" ) public List<Customer> getCustomers( ) { return customerRepository.findAll(); }
	@GetMapping( "/getAddresses" ) public List<Address> getAddresses( ) { return addressRepository.findAll(); }
	@GetMapping( "/getCities" ) public List<City> getCities( ) { return cityRepository.findAll(); }
	@GetMapping( "/getActors" ) public List<Actor> getActors( ) { return actorRepository.findAll(); }
	@GetMapping( "/getActorsCnt/{count}" ) public List<Actor> getActorsCnt(@PathVariable int count) {
		List<Actor> actors = actorRepository.findAll().subList(0, count);
		return actors;
	}

	@GetMapping( "/getCustomerRnd" ) public Customer getCustomerRnd( ) {

		int intMax = (int) customerRepository.count();
		Integer intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", customer_id: " + intId);

		return customerRepository.findById(intId).get();
	}
	@GetMapping( "/getAddressRnd" ) public Address getAddressRnd( ) {

		int intMax = (int) addressRepository.count();
		Integer intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", address_id: " + intId);

		return addressRepository.findById(intId).get();
	}
	@GetMapping( "/getCityRnd" ) public City getCityRnd( ) {

		int intMax = (int) cityRepository.count() + 1;
		Integer intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", city_id: " + intId);

		return cityRepository.findById(intId).get();
	}
	@GetMapping( "/getActorRnd" ) public Actor getActorRnd( ) {

		int intMax = (int) actorRepository.count();
		Integer actor_id = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", actor_id: " + actor_id);

		return actorRepository.findById(actor_id).get();
	}

	//#### regular MVC ####
	@GetMapping( "/showCustomers" ) public ModelAndView showCustomers( ) {

		ArrayList<Customer> customers = (ArrayList<Customer>) customerRepository.findAll();
		customers.sort((o1, o2) -> o1.getCustomer_id().compareTo(o2.getCustomer_id()));
		return new ModelAndView("customersList", "customers", customers);
	}
	@GetMapping( "/showAddresses" ) public ModelAndView showAddresses( ) {

		ArrayList<Address> addresses = (ArrayList<Address>) addressRepository.findAll();
		addresses.sort((o1, o2) -> o1.getAddress_id().compareTo(o2.getAddress_id()));
		return new ModelAndView("addressesList", "addresses", addresses);
	}
	@GetMapping( "/showCities" ) public ModelAndView showCities( ) {

		ArrayList<City> cities = (ArrayList<City>) cityRepository.findAll();
		cities.sort((o1, o2) -> o1.getCity_id().compareTo(o2.getCity_id()));
		return new ModelAndView("citiesList", "cities", cities);
	}
	@GetMapping( "/showActors" ) public ModelAndView showActors( ) {

		ArrayList<Actor> actors = (ArrayList<Actor>) actorRepository.findAll();
		actors.sort((o1, o2) -> o1.getActor_id().compareTo(o2.getActor_id()));
		return new ModelAndView("actorsList", "actors", actors);
	}

	@GetMapping( "/showCustomerRnd" ) public ModelAndView showCustomerRnd( ) {

		int intMax = (int) customerRepository.count();
		int intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", customer_id: " + intId);

		Customer customer = customerRepository.findById(intId).get();
		return new ModelAndView("customerShow", "customer", customer);
	}
	@GetMapping( "/showAddressRnd" ) public ModelAndView showAddressRnd( ) {

		int intMax = (int) addressRepository.count();
		int intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", actor_id: " + intId);

		Address address = addressRepository.findById(intId).get();
		return new ModelAndView("addressShow", "address", address);
	}
	@GetMapping( "/showCityRnd" ) public ModelAndView showCityRnd( ) {

		int intMax = (int) cityRepository.count();
		int intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", city_id: " + intId);

		City city = cityRepository.findById(intId).get();
		return new ModelAndView("cityShow", "city", city);
	}
	@GetMapping( "/showActorRnd" ) public ModelAndView showActorRnd( ) {

		int intMax = (int) actorRepository.count();
		int intId = random.nextInt(intMax);
		System.out.println("intMax: " + intMax + ", actor_id: " + intId);

		Actor actor = actorRepository.findById(intId).get();
		return new ModelAndView("actorShow", "actor", actor);
	}

	@GetMapping( "/showCustomer/{intId}" ) public ModelAndView showCustomer(@PathVariable int intId) {

		Customer customer = customerRepository.findById(intId).get();
		return new ModelAndView("customerShow", "customer", customer);
	}
	@GetMapping( "/showAddress/{intId}" ) public ModelAndView showAddress(@PathVariable int intId) {

		Address address = addressRepository.findById(intId).get();
		return new ModelAndView("addressShow", "address", address);
	}
	@GetMapping( "/showCity/{intId}" ) public ModelAndView showCity(@PathVariable int intId) {

		City city = cityRepository.findById(intId).get();
		return new ModelAndView("cityShow", "city", city);
	}
	@GetMapping( "/showActor/{intId}" ) public ModelAndView showActor(@PathVariable int intId) {

		Actor actor = actorRepository.findById(intId).get();
		return new ModelAndView("actorShow", "actor", actor);
	}

	@PostMapping( "/showActorMod" ) public ModelAndView showActorMod(@ModelAttribute Actor actor,
		@RequestParam String action) {

		HttpStatus httpStatus = NO_CONTENT;
		if ( action.equals("reset") ) {
			actor = new Actor();
			actor.setLast_update(Timestamp.from(Instant.now()));
			httpStatus = RESET_CONTENT;
			//
		} else if ( action.equals("save") ) {
			actor = actorRepository.save(actor);
			httpStatus = CREATED;
			//
		} else if ( action.equals("delete") ) {
			int actor_id = actor.getActor_id();
			if ( actor_id > 200 ) {
				actorRepository.deleteById(actor_id);
				actor = new Actor();
				actor.setLast_update(Timestamp.from(Instant.now()));
				httpStatus = GONE;
			} else {
				httpStatus = NOT_MODIFIED;
			}
		}

		return new ModelAndView("actorShow", "actor", actor);
	}
}
