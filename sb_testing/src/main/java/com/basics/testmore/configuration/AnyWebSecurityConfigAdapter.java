package com.basics.testmore.configuration; //.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
	import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

	https://www.marcobehler.com/guides/spring-security
	https://www.logicbig.com/tutorials/spring-framework/spring-security/custom-login-page.html
	https://www.baeldung.com/spring-boot-security-autoconfiguration
	https://spring.io/guides/topicals/spring-security-architecture
	https://www.programcreek.com/java-api-examples/?api=org.springframework.security.core.authority.SimpleGrantedAuthority
	https://stackoverflow.com/questions/49847791/java-spring-security-user-withdefaultpasswordencoder-is-deprecated/49847852
	https://stackoverflow.com/questions/24916894/serving-static-web-resources-in-spring-boot-spring-security-application
*/
@Configuration
@EnableWebSecurity
//@EnableWebMvc
public class AnyWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	private static final String ENCODED_PASSWORD = "$2a$10$AIUufK8g6EFhBcumRRV2L.AQNz3Bjp7oDQVFiO5JJMBFZQ6x2/R/2";
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//
		http.authorizeRequests()
				.antMatchers( "/", "/index" ).permitAll()
			//	.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage( "/login" )
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder AMB) throws Exception {
		//
		AMB.inMemoryAuthentication()
			.passwordEncoder(passwordEncoder())
			.withUser("user").password(ENCODED_PASSWORD).roles(USER);
	}

	@Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}