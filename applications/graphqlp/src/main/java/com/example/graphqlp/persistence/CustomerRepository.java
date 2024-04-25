package com.example.graphqlp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface CustomerRepository extends JpaRepository<Customer, Integer> { }

