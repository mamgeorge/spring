package com.basics.securing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// https://spring.io/guides/gs/securing-web/
@Configuration
@EnableWebSecurity
public class GeneralConfiguration {

	private static final String[] PATHS_PERMITTED = {"/", "/home"};
	private static final String PATH_LOGIN = "/login";

	@Bean public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(PATHS_PERMITTED).permitAll() // requestMatchers replaced antMatchers
				.anyRequest().authenticated() // permitAll
			)
			.formLogin(form -> form
				.loginPage(PATH_LOGIN) // .successHandler(successHandler()) // .defaultSuccessUrl("/")
				.permitAll()
			)
			.logout(LogoutConfigurer::permitAll)
		;

		return httpSecurity.build();
	}

	@SuppressWarnings( "deprecation" )
	@Bean public UserDetailsService userDetailsService( ) {

		UserDetails userDetails =
			User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}
}
