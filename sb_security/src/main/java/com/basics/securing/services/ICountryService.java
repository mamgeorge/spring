package com.basics.securing.services;

import com.basics.securing.model.Country;
import java.util.List;
import java.io.InputStream;

public interface ICountryService {

	Country findById(Long id);
	
	Country save(Country country);
	
	void load(InputStream inputStream);
	
	int getTotal();
	
	List<Country> findAll( );

	List<Country> findSome( int maxAmount );
}
