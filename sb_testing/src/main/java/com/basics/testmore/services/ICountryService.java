package com.basics.testmore.services;

import com.basics.testmore.model.Country;

import java.io.InputStream;
import java.util.List;

public interface ICountryService {

	Country findById(Long id);

	Country save(Country country);

	void load(InputStream inputStream);

	int getTotal();

	List<Country> findAll( );

	List<Country> findSome( int maxAmount );
}
