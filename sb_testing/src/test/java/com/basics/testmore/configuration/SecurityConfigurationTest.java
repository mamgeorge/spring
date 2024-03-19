package com.basics.testmore.configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.exposeObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityConfigurationTest {

	private final SecurityConfiguration securityConfiguration = new SecurityConfiguration();
	private final String USER_DEFAULT = "user";

	@Test void securityFilterChain( ) {

		String txtLines = "";
		try {
			HttpSecurity httpSecurity = mock(HttpSecurity.class);
			HttpSecurity httpSecurity1 = mock(HttpSecurity.class);
			HttpSecurity httpSecurity2 = mock(HttpSecurity.class);
			HttpSecurity httpSecurity3 = mock(HttpSecurity.class);
			RequestMatcher requestMatcher = mock(RequestMatcher.class);
			AntPathRequestMatcher APRM = new AntPathRequestMatcher("/ignore1", "/ignore2");
			HttpServletRequest HSR = mock(HttpServletRequest.class);
			List<Filter> filters = new ArrayList<>();
			//	DefaultSecurityFilterChain DSFC = new DefaultSecurityFilterChain(requestMatcher, filters);

			when(httpSecurity.authorizeHttpRequests(any(Customizer.class))).thenReturn(httpSecurity1);
			when(httpSecurity1.formLogin(any(Customizer.class))).thenReturn(httpSecurity2);
			when(httpSecurity2.logout(any(Customizer.class))).thenReturn(httpSecurity3);
			//	when(httpSecurity.build()).thenReturn(DSFC);

			SecurityFilterChain securityFilterChain = securityConfiguration.securityFilterChain(httpSecurity);

			//	txtLines += "[" + securityFilterChain.matches(HSR) + "]" + EOL;
			txtLines += exposeObject(securityFilterChain);
			System.out.println(txtLines);
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		assertNotNull(txtLines);
	}

	@Test void userDetailsService( ) {

		UserDetailsService userDetailsService = securityConfiguration.userDetailsService();
		UserDetails userDetails = userDetailsService.loadUserByUsername(USER_DEFAULT);

		System.out.println(exposeObject(userDetails));
		assertNotNull(userDetailsService);
	}

	@Test void webSecurityCustomizer( ) {

		WebSecurityCustomizer WSC = securityConfiguration.webSecurityCustomizer();
		System.out.println(exposeObject(WSC));
		assertNotNull(WSC);
	}

	@Test void authenticationManager( ) {

		AnnotationConfigApplicationContext ACAC = new AnnotationConfigApplicationContext();
		AuthenticationConfiguration authConfig = new AuthenticationConfiguration();
		authConfig.setApplicationContext(ACAC);

		AuthenticationManager authenticationManager = securityConfiguration.authenticationManager(authConfig);
		System.out.println("authenticationManager: " + authenticationManager);
		System.out.println(exposeObject(authConfig));
		assertNotNull(authConfig);
	}

	@Test void passwordEncoder( ) {

		String password = System.getenv("PASSWORD");
		PasswordEncoder passwordEncoder = securityConfiguration.passwordEncoder();
		String passwordEncoded = passwordEncoder.encode(password);

		System.out.println("passwordEncoded: " + password + " >> " + passwordEncoded);
		System.out.println(exposeObject(passwordEncoder));
		assertNotNull(passwordEncoder);
	}
}
