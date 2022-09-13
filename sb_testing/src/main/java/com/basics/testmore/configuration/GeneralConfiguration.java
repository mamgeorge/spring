package com.basics.testmore.configuration; //.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class GeneralConfiguration {

	// https://stackoverflow.com/questions/41480102/how-spring-security-filter-chain-works
	@Bean public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// authorizeHttpRequests takes Customizer functional interface AuthorizationManagerRequestMatcherRegistry ?
		httpSecurity
			.authorizeHttpRequests( requests -> requests.antMatchers("/").hasRole("USER") )
			.formLogin(withDefaults() ) // form -> form.loginPage("/login").permitAll() // .successHandler(successHandler()) // .defaultSuccessUrl("/")
			.logout( logout -> logout.permitAll())
		;

		return httpSecurity.build();
	}

	@Bean public UserDetailsService userDetailsService( ) {

		UserDetails userDetails =
			User.withDefaultPasswordEncoder()
				.username("user")
				.password("secret")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers("/resources/**");
	}
}