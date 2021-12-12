package com.basics.testmore.configuration; //.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


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

/*
	public void addViewControllers(ViewControllerRegistry vcRegistry) {
		//
		ViewControllerRegistration vcr = vcRegistry.addViewController("/login");
		vcr.setViewName("login");
	}

	@Bean public ViewResolver viewResolver() { return freemarkerViewResolver(); }

	@Bean public FreeMarkerViewResolver freemarkerViewResolver() {
		//
		FreeMarkerViewResolver fmViewResolver = new FreeMarkerViewResolver();
		fmViewResolver.setCache(true);
		fmViewResolver.setPrefix("");
		fmViewResolver.setSuffix(".ftlh");
		//
		return fmViewResolver;
	}	

	protected void configure(AuthenticationManagerBuilder AMB) throws Exception {
		//
		// Authentication with Spring Security
		//
		//
		AMB.inMemoryAuthentication()
		//	.passwordEncoder( bcPasswordEncoder() )
			.withUser("user")
		//	.password(ENCODED_PASSWORD)
			.password("secret123")
			.roles(USER);
		AMB.inMemoryAuthentication()
			.withUser("admin")
		//	.password( dgPasswordEncoder.encode("admin") )
			.password( ("admin") )
			.roles(USER , ADMIN);
	}

	@Bean public PasswordEncoder bcPasswordEncoder() { return new BCryptPasswordEncoder(); }
	@Bean public PasswordEncoder dgPasswordEncoder() { return PasswordEncoderFactories.createDelegatingPasswordEncoder(); }

	@Override public void addViewControllers(ViewControllerRegistry vcRegistry) {
		//
		ViewControllerRegistration vcr = vcRegistry.addViewController("/logins");
		vcr.setViewName("login");
	}

	@Bean public FreeMarkerViewResolver freemarkerViewResolver() {
		//
		FreeMarkerViewResolver fmViewResolver = new FreeMarkerViewResolver();
		fmViewResolver.setCache(true);
		fmViewResolver.setPrefix("");
		fmViewResolver.setSuffix(".ftlh");
		//
		return fmViewResolver;
	}

	@Bean public ViewResolver viewResolver() { return freemarkerViewResolver(); }

	@Bean WebMvcConfigurer anyWebMvcConfigurer() {
		//
		// https://www.logicbig.com/tutorials/spring-framework/spring-security/custom-login-page.html
		return new v() {

			@Override public void addViewControllers(ViewControllerRegistry vcRegistry) {
				ViewControllerRegistration vcr = vcRegistry.addViewController("/logins");
				vcr.setViewName("login");
			}
		};
	}

	@Bean public ViewResolver viewResolver() {
		//
		// https://www.logicbig.com/tutorials/spring-framework/spring-security/custom-login-page.html
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		//
		return viewResolver;
	}

	public void configureDefaultServletHandling( DefaultServletHandlerConfigurer dshConfigurer ) { dshConfigurer.enable(); }

	@Bean @Override
	public UserDetailsService userDetailsService() {
		//
		UserDetails userDetails = null;
		userDetails = User.withDefaultPasswordEncoder().username("user").password("secret123").roles(USER).build();
		// userDetails = findUserByUsername("user").build( );
		//
		return new InMemoryUserDetailsManager(userDetails);
	}

	private User findUserByUsername(String username) {
		//
		User user = null;
		//Role roleAdmin = new Role("ADMIN");
		//Role roleUser = new Role("USER");
		//SimpleGrantedAuthority sgaAdmn = new SimpleGrantedAuthority("ADMIN");
		//SimpleGrantedAuthority sgaUser = new SimpleGrantedAuthority("USER");
		if ( username==null || username.equals("") )	{ } else {
			//
		//	if ( username.equalsIgnoreCase("MLG") )		{ user = new User( username, "MLG"		, ADMIN ); }
		//	if ( username.equalsIgnoreCase("admin") )	{ user = new User( username, "admin"	, ADMIN ); }
		//	if ( username.equalsIgnoreCase("user") )	{ user = new User( username, "pass"		, USER ); }
		}
		return user;
	}
*/
}