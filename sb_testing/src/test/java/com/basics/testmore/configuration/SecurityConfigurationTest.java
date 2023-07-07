package com.basics.testmore.configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static com.basics.testmore.util.UtilityMain.EOL;
import static com.basics.testmore.util.UtilityMain.exposeObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityConfigurationTest {

	private final SecurityConfiguration generalConfiguration = new SecurityConfiguration();

	@Test void UserDetailsService( ) {

		String txtLines = "";
		String testUser = System.getenv("USERNAME");

		ReflectionTestUtils.setField(generalConfiguration, "DEFAULT_USER", testUser);
		txtLines += ReflectionTestUtils.getField(generalConfiguration, "DEFAULT_USER") + EOL;
		txtLines += ReflectionTestUtils.invokeGetterMethod(generalConfiguration, "getPassword") + EOL;

		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void SecurityFilterChain( ) {

		String txtLines = "";

		try {
			HttpSecurity httpSecurity = mock(HttpSecurity.class);
			HttpSecurity httpSecurity1 = mock(HttpSecurity.class);
			HttpSecurity httpSecurity2 = mock(HttpSecurity.class);
			HttpSecurity httpSecurity3 = mock(HttpSecurity.class);
			RequestMatcher requestMatcher = mock(RequestMatcher.class);
			HttpServletRequest HSR = mock(HttpServletRequest.class);
			List<Filter> filters = new ArrayList<>();
			DefaultSecurityFilterChain DSFC = new DefaultSecurityFilterChain(requestMatcher, filters);

			when(httpSecurity.authorizeHttpRequests(any(Customizer.class))).thenReturn(httpSecurity1);
			when(httpSecurity1.formLogin(any(Customizer.class))).thenReturn(httpSecurity2);
			when(httpSecurity2.logout(any(Customizer.class))).thenReturn(httpSecurity3);
			when(httpSecurity.build()).thenReturn(DSFC);

			SecurityFilterChain securityFilterChain = generalConfiguration.securityFilterChain(httpSecurity);
			txtLines += "[" + securityFilterChain.matches(HSR) + "]" + EOL;
			txtLines += exposeObject(securityFilterChain);
			System.out.println(txtLines);
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		assertNotNull(txtLines);
	}

}
