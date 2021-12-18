package com.basics.testmore.services;

import com.basics.testmore.model.City;
import java.util.List;

public interface ICityService {

	City findById(Long id);	

	City save(City city);

	// void load(InputStream inputStream);

	List<City> findAll( );
	
	List<City> findSome( int maxAmount );	
}
