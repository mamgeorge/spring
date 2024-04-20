package com.example.graphqlp.configuration;

import com.example.graphqlp.persistence.ActorService;
import com.example.graphqlp.persistence.Address;
import com.example.graphqlp.persistence.AddressService;
import com.example.graphqlp.persistence.City;
import com.example.graphqlp.persistence.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.Random;

@Controller
public class GraphqlpController {

	private final AddressService addressService;
	private final ActorService actorService;
	private final CityService cityService;
	private final Random random = new Random();

	@Autowired public GraphqlpController(AddressService addressService,
		ActorService actorService, CityService cityService) {

		this.addressService = addressService;
		this.actorService = actorService;
		this.cityService = cityService;
	}

	@GetMapping( { "/", "/home", "/root" } )
	public ModelAndView root( ) {

		String timer = Instant.now().toString();
		System.out.println("timer: " + timer);
		ModelAndView MAV = new ModelAndView("index");
		return MAV;
	}

	//############
	@QueryMapping public Address addressById(@Argument int id) {

		Address address = addressService.findById(id);
		return address;
	}

	@SchemaMapping public City city(Address address) {

		City city = cityService.findById(address.getCity_id());
		return city;
	}

	@QueryMapping public City cityById(@Argument int id) {

		City city = cityService.findById(id);
		return city;
	}

}
