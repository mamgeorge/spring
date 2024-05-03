package com.example.graphqld.persistence;

import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActorService {

	@Autowired GraphQLWebClient graphQLWebClient;

	public static final String QUERY_ACT = "query actById { actorById( actor_id: ## ) "
		+ "{ actor_id first_name last_name last_update } }";

	public static final String QUERY_ACTVAR = "query actById($actor_id: ID) { actorById( actor_id: $actor_id ) "
		+ "{ actor_id first_name last_name last_update } }";

	public Actor getActorbyId(Integer id) {

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(id));
		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(queryAct).build();

		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		Actor actor = graphQLResponse.get("actorById", Actor.class);
		return actor;
	}

	public Actor getActorbyIdVar(Integer id) {

		Map<String, Object> variables = new HashMap<>();
		variables.put("actor_id", String.valueOf(id));

		GraphQLRequest graphQLRequest = GraphQLRequest.builder()
			.query(QUERY_ACTVAR)
			.variables(variables)
			.build();

		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		Actor actor = graphQLResponse.get("actorById", Actor.class);
		return actor;
	}

	public Object getObjectbyId(Integer id) {

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(id));
		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(queryAct).build();

		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		Object object = graphQLResponse.getAt("actorById");
		return object;
	}
}
