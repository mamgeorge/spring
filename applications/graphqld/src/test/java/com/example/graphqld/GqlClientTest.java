package com.example.graphqld;

import com.example.graphqld.persistence.Actor;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.com.google.common.collect.Maps;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.example.graphqld.GqlClientAppTests.formatJson;
import static com.example.graphqld.GqlClientAppTests.formatObject;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class GqlClientTest {

	String URL = "http://localhost:8080/graphql";
	String QUERY_ACT = "query actById { actorById(actor_id: 10) "
		+ "{ actor_id first_name last_name last_update } }";

	@Test void restClient_test( ) {

		// https://docs.spring.io/spring-framework/reference/integration/rest-clients.html
		String[] urls = { "http://ip.jsontest.com",
			"https://dummyjson.com/user/2", "https://dummyjson.com/users",
			"https://dummyjson.com/product/2", "https://dummyjson.com/products",
			"https://jsonplaceholder.typicode.com/posts/2", "https://jsonplaceholder.typicode.com/posts"
		};

		Map<String, String> map = Maps.newHashMap();
		map.put("address", "14.917313,-23.511313");
		map.put("email", "YOUR_EMAIL_HERE");

		RestClient restClient = RestClient.builder().build();
		RestClient.ResponseSpec responseSpec = restClient.get().uri(urls[5]).retrieve();
		ResponseEntity<String> responseEntity = responseSpec.toEntity(String.class);
		String json = responseEntity.getBody();

		String txtLines = formatJson(json);
		System.out.println(txtLines);
		assertTrue(true);
	}

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
		Actor actor = graphQLResponse.get("actorById", Actor.class);
		System.out.println(formatObject(actor));

		assertTrue(true);
	}
}
