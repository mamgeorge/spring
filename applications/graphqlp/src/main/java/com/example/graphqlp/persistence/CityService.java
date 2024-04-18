package com.example.graphqlp.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

	public final CityRepository cityRepository;

	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public List<City> findAll( ) { return (List<City>) cityRepository.findAll(); }

	public City findById(Integer id) { return cityRepository.findById(id).get(); } // getReferenceById

	public long getMaxId( ) { return cityRepository.count(); }
}
