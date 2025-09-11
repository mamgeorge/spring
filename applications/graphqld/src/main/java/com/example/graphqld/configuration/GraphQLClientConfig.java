package com.example.graphqld.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GraphQLClientConfig {

	@Value( "${graphql.client.url}" )
	private String graphqlUrl;

	@Bean
	public GraphQLWebClient graphQLWebClient(ObjectMapper objectMapper) {
		WebClient webClient = WebClient.builder()
			.baseUrl(graphqlUrl)
			.build();
		return GraphQLWebClient.newInstance(webClient, objectMapper);
	}
}
