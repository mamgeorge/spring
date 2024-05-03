package com.example.graphqld.persistence;

import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

	@Autowired GraphQLWebClient graphQLWebClient;

	private final String QUERY_ACT = "query actById { actorById( actor_id: ## ) "
		+ "{ actor_id first_name last_name last_update } }";

	public Actor getActorbyId(Integer id) {

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(id));
		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(queryAct).build();

		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		Actor actor = graphQLResponse.get("actorById", Actor.class);
		return actor;
	}
}
