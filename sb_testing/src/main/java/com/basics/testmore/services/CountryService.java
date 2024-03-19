package com.basics.testmore.services;

import com.basics.testmore.model.Country;
import com.basics.testmore.repository.CountryRepository;
import com.basics.testmore.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

// @Service annotation declares this class to be a service class (a class that provides business services)
// @Autowired annotation marks the repository field to be injected with correct Repository
@Service
public class CountryService implements ICountryService {

	private static final Logger LOGGER = Logger.getLogger(CountryService.class.getName());

	private int total = 0;

	@Autowired private CountryRepository countryRepository;

	@Override public Country findById(Long id) { return countryRepository.findById(id).get(); }

	@Override public Country save(Country country) { return countryRepository.save(country); }

	@Override public void load(InputStream inputStream) {
		//
		try {
			LOGGER.info("loading inputStream into saved");
			List<Country> countries = CSVHelper.csvToCountries(inputStream);
			total = countries.size();
			countryRepository.saveAll(countries);
		}
		catch (Exception ex) { LOGGER.warning(ex.getMessage()); }
	}

	@Override public int getTotal( ) { return total; }

	@Override public List<Country> findAll( ) { return countryRepository.findAll(); }

	@Override public List<Country> findSome(int maxAmount) {
		//
		List<Country> countries = this.findAll();
		ArrayList<Country> subCountries = new ArrayList<>(countries.subList(0, maxAmount));
		Collections.sort(subCountries, Comparator.comparing(Country::getContinent));
		return subCountries;
	}

	/*
	@Override public List<Country> findThis( ) { 
		//
        var listCountries = new ArrayList<Country>();
        listCountries.add( new Country( 1L, "Bratislava", 432000 ) );
		listCountries.add( new Country( 2L, "Budapest", 1759000 ) );
        return listCountries;	
	}
	*/
}