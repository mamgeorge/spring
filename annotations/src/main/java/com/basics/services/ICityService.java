package com.basics.services;

import com.basics.model.City;

import java.util.List;

public interface ICityService {

	City findById(Long id);

	City save(City city);

	List<City> findAll();
}
