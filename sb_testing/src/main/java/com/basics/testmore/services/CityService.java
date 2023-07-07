package com.basics.testmore.services;

import com.basics.testmore.model.City;
import com.basics.testmore.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CityService implements ICityService {

	private static final Logger LOGGER = Logger.getLogger( CountryService.class.getName( ) );

	@Autowired private CityRepository cityRepository;

	@Override public City findById(Long id) { return cityRepository.findById(id).get(); }

	@Override public City save(City city) { return cityRepository.save(city); }

	@Override public List<City> findAll( ) { return ( List<City> ) cityRepository.findAll( ); }

	@Override public List<City> findSome( int maxAmount ) { return new ArrayList<City>( ); } // not implemented

	/*
	@Override public List<City> findThis( ) { 
		//
        var listCities = new ArrayList<City>();
        listCities.add( new City( 1L, "Bratislava", 432000 ) );
		listCities.add( new City( 2L, "Budapest", 1759000 ) );
        return listCities;	
	}
	*/
}