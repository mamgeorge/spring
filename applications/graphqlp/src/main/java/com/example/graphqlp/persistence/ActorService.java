package com.example.graphqlp.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

	public final ActorRepository actorRepository;

	@Autowired
	public ActorService(ActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}

	public List<Actor> findAll( ) {

		Iterable<Actor> iterable = actorRepository.findAll();
		List<Actor> actors = new ArrayList<>();
		iterable.forEach(actors::add);
		return actors;
	}

	public Actor findById(Integer id) {

		Actor actor = new Actor();
		Optional<Actor> optional =  actorRepository.findById(id);
		if ( optional.isPresent() ) {
			actor = optional.get();
		} else {
//			city = cityRepository.getOne(id);
//			city = cityRepository.getReferenceById(id);
//			city = cityRepository.getOne(id.intValue());
//			city = cityRepository.getReferenceById(id.intValue());
			//city = cityRepository.findById(id.intValue()).get();
			//city = cityRepository.findAll().get(id.intValue());
		}
		return actor;
	} // getReferenceById

	public long getMaxId( ) { return actorRepository.count(); }
}
