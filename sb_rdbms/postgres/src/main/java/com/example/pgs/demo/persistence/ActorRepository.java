package com.example.pgs.demo.persistence;

import com.example.pgs.demo.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> { }
