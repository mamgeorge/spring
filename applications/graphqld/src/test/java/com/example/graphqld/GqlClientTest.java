package com.example.graphqld;

import com.example.graphqld.persistence.Actor;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.com.google.common.collect.Maps;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.example.graphqld.GqlClientAppTests.formatJson;
import static com.example.graphqld.GqlClientAppTests.formatObject;
import static com.example.graphqld.persistence.ActorService.QUERY_ACT;
import static com.example.graphqld.persistence.ActorService.QUERY_ACTVAR;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class GqlClientTest {

	String URL = "http://localhost:8080/graphql";
	String[] URLs = {
		"http://ip.jsontest.com",
		"https://dummyjson.com/user/2",
		"https://dummyjson.com/users",
		"https://dummyjson.com/product/2",
		"https://dummyjson.com/products",
		"https://jsonplaceholder.typicode.com/posts/2",
		"https://jsonplaceholder.typicode.com/posts"
	};

	@Test void restClient_test( ) {

		// https://docs.spring.io/spring-framework/reference/integration/rest-clients.html
		Map<String, String> map = Maps.newHashMap();
		map.put("address", "14.917313,-23.511313");
		map.put("email", "YOUR_EMAIL_HERE");

		RestClient restClient = RestClient.builder().build();
		RestClient.ResponseSpec responseSpec = restClient.get().uri(URLs[5]).retrieve();
		ResponseEntity<String> responseEntity = responseSpec.toEntity(String.class);
		String json = responseEntity.getBody();

		String txtLines = formatJson(json);
		System.out.println(txtLines);
		assertTrue(true);
	}

	@Test void graphQLRequest_test( ) {

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(10));
		WebClient webClient = WebClient.builder()
			.baseUrl(URL)
			.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.build();
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		GraphQLWebClient graphQLWebClient = GraphQLWebClient.newInstance(webClient, objectMapper);

		GraphQLRequest graphQLRequest = GraphQLRequest.builder().query(queryAct).build();
		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();

		Actor actor = graphQLResponse.get("actorById", Actor.class);
		System.out.println(formatObject(actor));
		assertTrue(true);
	}

	@Test void graphQLRequest_var( ) {

		int id = 10;
		WebClient webClient = WebClient.builder()
			.baseUrl(URL)
			.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.build();
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		GraphQLWebClient graphQLWebClient = GraphQLWebClient.newInstance(webClient, objectMapper);

		Map<String, Object> variables = new HashMap<>();
		variables.put("actor_id", String.valueOf(id));

		GraphQLRequest graphQLRequest = GraphQLRequest.builder()
			.query(QUERY_ACTVAR)
			.variables(variables)
			.build();
		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();

		Actor actor = graphQLResponse.get("actorById", Actor.class);
		System.out.println(formatObject(actor));
		assertTrue(true);
	}

	@Test void graphQLRequest_obj( ) {

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(10));
		WebClient webClient = WebClient.builder()
			.baseUrl(URL)
			.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.build();
		ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
		GraphQLWebClient graphQLWebClient = GraphQLWebClient.newInstance(webClient, objectMapper);

		GraphQLRequest graphQLRequest = GraphQLRequest.builder().query(queryAct).build();
		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();

		Object object = graphQLResponse.getAt("actorById");
		System.out.println(formatObject(object));
		assertTrue(true);
	}

	@Test void httpGraphQlClient_test( ) {

		String queryAct = QUERY_ACT.replaceAll("##", String.valueOf(10));
		WebClient webClient = WebClient.builder()
			.baseUrl(URL)
			.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.build();
		HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder(webClient)
			.build();

		Mono<Actor> monoActor = httpGraphQlClient
			.document(queryAct)
			.retrieve("actorById")
			.toEntity(Actor.class);

		Actor actor = monoActor.block();
		System.out.println(formatObject(actor));
		assertTrue(true);
	}
}
