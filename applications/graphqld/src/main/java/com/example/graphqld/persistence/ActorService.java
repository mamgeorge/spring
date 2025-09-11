package com.example.graphqld.persistence;

import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActorService {

	@Autowired GraphQLWebClient graphQLWebClient;

	public static final String QUERY_ACTORS = "query actList { actors \"\n" +
		"\t\t+ \"{ actor_id first_name last_name last_update } }";

	public static final String QUERY_ACT = "query actById { actorById( actor_id: ## ) "
		+ "{ actor_id first_name last_name last_update } }";

	public static final String QUERY_ACTVAR =
		"query actById($actor_id: ID) { actorById( actor_id: $actor_id ) "
			+ "{ actor_id first_name last_name last_update } }";

	public Actor[] getActors( ) {

		Actor[] actors = null;

		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(QUERY_ACTORS).build();
		try {
			GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
			actors = graphQLResponse.get("actors", Actor[].class);
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		if ( actors == null ) { actors = new Actor[0]; }
		return actors;
	}

	public Actor getActorbyId(Integer id) {

		Actor actor = null;

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(id));
		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(queryAct).build();
		try {
			GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
			actor = graphQLResponse.get("actorById", Actor.class);
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }
		if ( actor == null ) { actor = new Actor(); }
		return actor;
	}

	public Actor getActorbyIdVar(Integer id) {

		Actor actor = null;
		Map<String, Object> variables = new HashMap<>();
		variables.put("actor_id", String.valueOf(id));

		GraphQLRequest graphQLRequest = GraphQLRequest.builder()
			.query(QUERY_ACTVAR)
			.variables(variables)
			.build();
		try {
			GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
			actor = graphQLResponse.get("actorById", Actor.class);
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		if ( actor == null ) { actor = new Actor(); }
		return actor;
	}

	public Object getObjectbyId(Integer id) {

		Object object = null;

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(id));
		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(queryAct).build();
		try {
			GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
			object = graphQLResponse.getAt("actorById");
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		if ( object == null ) { object = new Object(); }
		return object;
	}
}
