package com.example.graphqld;

import com.example.graphqlp.persistence.Actor;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class GraphQlClientTest {

	String URL = "http://localhost:8080/graphql/";
	String QUERY_ACT = "query actById { actorById(actor_id: 10) "
		+ "{ actor_id first_name last_name } }";

	@Test void graphQLRequest_test( ) {

		WebClient webClient = WebClient.builder()
			.baseUrl(URL)
			.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.build();
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		GraphQLWebClient graphQLWebClient = GraphQLWebClient.newInstance(webClient, objectMapper);

		GraphQLRequest graphQLRequest = GraphQLRequest
			.builder().query(QUERY_ACT).build();

		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		Object object = graphQLResponse.get("actor", Actor.class);
		System.out.println(object.toString());

		assertTrue(true);
	}
}
