package com.example.graphqlp.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.GONE;

@Service
public class ActorService {

	public final ActorRepository actorRepository;

	@Autowired
	public ActorService(ActorRepository actorRepository) { this.actorRepository = actorRepository; }

	public List<Actor> findAll( ) { return actorRepository.findAll(); }

	public Actor findById(Integer actor_id) { return actorRepository.findById(actor_id).get(); } // getReferenceById

	public Actor save(Actor actor) { actor = actorRepository.save(actor); return actor; }

	public HttpStatus delete(Integer actor_id) { actorRepository.deleteById(actor_id); return GONE; }

	public long getMaxId( ) { return actorRepository.count(); }

	// not needed; here for showing an "optional" variation
//	public Actor findByIdOptional(Integer id) {
//
//		Actor actor = new Actor();
//		Optional<Actor> optional = actorRepository.findById(id);
//		if ( optional.isPresent() ) {
//			actor = optional.get();
//		} else { System.out.println("find by some other way: getOne, getReferenceById"); }
//		return actor;
//	}
}