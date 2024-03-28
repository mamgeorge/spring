package com.example.embedded.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// class not needed for basic implementation
@Configuration
public class EmbeddedConfiguration {

	@Bean @ConfigurationProperties( prefix = "datasource" ) // @Primary
	public DataSource getDataSource( ) {
		return DataSourceBuilder.create().build();
	}
}
