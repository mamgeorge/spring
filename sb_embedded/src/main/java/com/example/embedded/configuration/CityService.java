package com.example.embedded.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service annotation declares CityService to be a service class; a class that provides business services
// @Autowired annotation marks cityRepository field to be injected with CityRepository

@Service
public class CityService implements ICityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public City findById(Long id) { return cityRepository.findById(id).get(); }

	@Override
	public City save(City city) { return cityRepository.save(city); }

	@Override
	public List<City> findAll() { return (List<City>) cityRepository.findAll(); }

	/*
	public List<City> findAll( ) { 
		//
        var listCities = new ArrayList<City>();
        listCities.add( new City( 1L, "Bratislava", 432000 ) );
		listCities.add( new City( 2L, "Budapest", 1759000 ) );
        return listCities;	
	}
	*/
}