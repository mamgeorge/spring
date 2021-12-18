package com.basics.securing; //.configuration;

import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

// http://localhost:8080/swagger-ui/
@Configuration
@Import(SpringDataRestConfiguration.class) 
public class AnySpringFoxConfig {
	//
	// note Spring only requires @EnableSwagger2WebMvc & autoconfig of resource handlers
    @Bean
    public Docket api() {
        return new Docket( DocumentationType.SWAGGER_2 )
          .select()
          .apis( RequestHandlerSelectors.any() )
          .paths( PathSelectors.any() )
          .build();
    }
}
