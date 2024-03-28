package com.example.embedded.model;

import java.util.List;

public interface ICityService {

	City findById(Long id);

	City save(City city);

	List<City> findAll();
}
