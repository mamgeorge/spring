package com.basics.testmore.configuration; //.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

// http://localhost:8080/swagger-ui/
@Configuration @Import(SpringDataRestConfiguration.class) 
public class AnySpringFoxConfig {
	//
	// note Spring only requires @EnableSwagger2WebMvc & autoconfig of resource handlers
	@Bean public Docket customApi() {
		Docket docket = new Docket( DocumentationType.SWAGGER_2 )
			.select()
			.apis( RequestHandlerSelectors.any() )
			.paths( PathSelectors.any() )
			.build();
		return docket;
	}
}
