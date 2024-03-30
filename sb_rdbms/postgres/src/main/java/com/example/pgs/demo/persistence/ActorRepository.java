package com.example.pgs.demo.persistence;

import com.example.pgs.demo.model.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> { }
