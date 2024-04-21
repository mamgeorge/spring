package com.example.graphqlp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface ActorRepository extends JpaRepository<Actor, Integer> { }

