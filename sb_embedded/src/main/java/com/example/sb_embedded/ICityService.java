package com.example.sb_embedded;

import java.util.List;

public interface ICityService {

	City findById(Long id);

	City save(City city);

	List<City> findAll();
}
