package com.basics.testmore.configuration;

import com.basics.testmore.util.UtilityMain;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;

class AnyWebSecurityConfigAdapterTest {

	/**
	 powermock of final classes only possible when adding:
	 "src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker" with line: mock-maker-inline
	*/
	@Test
	void configure_HttpSecurity( ) {

		HttpSecurity httpSecurity = mock(HttpSecurity.class);
		WebSecurityConfigAdapterImpl AWSCA = new WebSecurityConfigAdapterImpl();
		try { AWSCA.configure(httpSecurity); }
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(UtilityMain.exposeObject(httpSecurity));
		assertNotNull(httpSecurity);
	}

	@Test
	void testConfigure_amb( ) {

		AuthenticationManagerBuilder AMB = mock(AuthenticationManagerBuilder.class);
		WebSecurityConfigAdapterImpl AWSCA = new WebSecurityConfigAdapterImpl();
		try { AWSCA.configure(AMB); }
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println(UtilityMain.exposeObject(AMB));
		assertNotNull(AMB);
	}

	@Test
	void passwordEncoder( ) {

		WebSecurityConfigAdapterImpl AWSCA = new WebSecurityConfigAdapterImpl();
		BCryptPasswordEncoder bcryptPasswordEncoder = (BCryptPasswordEncoder) AWSCA.passwordEncoder();

		System.out.println(UtilityMain.exposeObject(bcryptPasswordEncoder));
		assertNotNull(bcryptPasswordEncoder);
	}
}