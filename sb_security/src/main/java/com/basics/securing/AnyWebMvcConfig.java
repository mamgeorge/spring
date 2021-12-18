package com.basics.securing; // .configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AnyWebMvcConfig implements WebMvcConfigurer {

	public void addViewControllers( ViewControllerRegistry vcRegistry ) {
		//
		vcRegistry.addViewController("/").setViewName("home");
		vcRegistry.addViewController("/home").setViewName("home");
		vcRegistry.addViewController("/hello").setViewName("hello");
		vcRegistry.addViewController("/login").setViewName("login");
		vcRegistry.addViewController("/countries").setViewName("countries");
	}
}

