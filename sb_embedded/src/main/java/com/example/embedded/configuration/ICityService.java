package com.example.embedded.configuration;

import java.util.List;

public interface ICityService {

	City findById(Long id);

	City save(City city);

	List<City> findAll();
}
