package com.example.pgs.demo.persistence;

import com.example.pgs.demo.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

	private final ActorRepository actorRepository;

	@Autowired
	public ActorService(ActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}

	public List<Actor> findAll( ) { return (List<Actor>) actorRepository.findAll(); }

	public Actor findById(Long id) { return actorRepository.findById(id).get(); }

	public long getMaxId( ) { return actorRepository.count(); }
}
