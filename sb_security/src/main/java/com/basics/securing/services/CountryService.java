package com.basics.securing.services;

import com.basics.securing.model.Country;
import com.basics.securing.repository.CountryRepository;
import com.basics.securing.utils.CSVHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.Comparator;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service annotation declares CityService to be a service class; a class that provides business services
// @Autowired annotation marks cityRepository field to be injected with CityRepository
@Service
public class CountryService implements ICountryService {

	private static final Logger LOGGER = Logger.getLogger( CountryService.class.getName( ) );

	private int total = 0;

	@Autowired
	private CountryRepository countryRepository;

	@Override public Country findById(Long id) { return countryRepository.findById(id).get(); }

	@Override public Country save(Country country) { return countryRepository.save(country); }

	@Override public void load( InputStream inputStream ) {
		//
		try {
			LOGGER.info( "loading inputStream into saved" );
			List<Country> countries = CSVHelper.csvToCountries( inputStream );
			total = countries.size();
			countryRepository.saveAll( countries );
		} 
		catch (Exception ex) { LOGGER.warning( ex.getMessage( ) ); }
	}

	@Override public int getTotal( ) { return total; }

	@Override public List<Country> findAll( ) { return ( List<Country> ) countryRepository.findAll( ); }

	@Override public List<Country> findSome( int maxAmount ) { 
		//
		List<Country> countries = ( List<Country> ) countryRepository.findAll( ); 
		ArrayList<Country> subCountries = new ArrayList<Country>( countries.subList( 0 , maxAmount ) );
		Collections.sort( subCountries , Comparator.comparing(Country::getContinent) );
		return subCountries; 
	}

	/*
	public List<Country> findAll( ) { 
		//
        var listCountries = new ArrayList<Country>();
        listCountries.add( new Country( 1L, "Bratislava", 432000 ) );
		listCountries.add( new Country( 2L, "Budapest", 1759000 ) );
        return listCountries;	
	}
	*/
}