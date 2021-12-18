package com.basics.securing.repository;

import com.basics.securing.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import org.springframework.data.repository.CrudRepository;

@Repository
// public interface CountryRepository extends CrudRepository<Country, Long> { }
public interface CountryRepository extends JpaRepository<Country, Long> { }

