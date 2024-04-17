package com.example.graphqld.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

	public final CityRepository cityRepository;

	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public List<City> findAll( ) {

		Iterable<City> iterable = cityRepository.findAll();
		List<City> cities = new ArrayList<>();
		iterable.forEach(cities::add);
		return cities;
	}

	public City findById(Integer id) {

		City city = new City();
		Optional<City> optional =  cityRepository.findById(id);
		if ( optional.isPresent() ) {
			city = optional.get();
		} else {
//			city = cityRepository.getOne(id);
//			city = cityRepository.getReferenceById(id);
//			city = cityRepository.getOne(id.intValue());
//			city = cityRepository.getReferenceById(id.intValue());
			//city = cityRepository.findById(id.intValue()).get();
			//city = cityRepository.findAll().get(id.intValue());
		}
		return city;
	} // getReferenceById

	public long getMaxId( ) { return cityRepository.count(); }
}
