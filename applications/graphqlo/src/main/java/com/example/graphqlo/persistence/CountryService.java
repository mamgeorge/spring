package com.example.graphqlo.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

	private final CountryRepository countryRepository;

	@Autowired
	public CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public List<Country> findAll( ) { return (List<Country>) countryRepository.findAll(); }

	public Country findById(String id) { return countryRepository.findById(id).get(); } // getReferenceById

	public long getMaxId( ) { return countryRepository.count(); }
}
