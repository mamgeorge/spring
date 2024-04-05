package com.example.graphqlo.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

	private final CityRepository cityRepository;

	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public List<City> findAll( ) { return (List<City>) cityRepository.findAll(); }

	public City findById(Integer id) {

		City city;
		city =  cityRepository.findById(id).get();
		// city = cityRepository.getOne(id);
		return city;
	} // getReferenceById

	public long getMaxId( ) { return cityRepository.count(); }
}
