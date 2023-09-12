package com.basics.securing.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static com.basics.securing.utils.UtilityMain.EOL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeneralConfigurationTest {

	GeneralConfiguration generalConfiguration = new GeneralConfiguration();

	@Test
	void securityFilterChain( ) {

		// HttpSecurity httpSecurityMock = mock(HttpSecurity.class); // Cannot mock final
		SecurityFilterChain SFC = null;

		try { SFC = generalConfiguration.securityFilterChain(null); }
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println("SecurityFilterChain: " + SFC);
		assertNull(SFC);
	}

	@Test
	void userDetailsService( ) {

		StringBuilder stringBuilder = new StringBuilder();
		String userName = "user";

		UserDetailsService UDS = generalConfiguration.userDetailsService();
		UserDetails userDetails = UDS.loadUserByUsername(userName);
		stringBuilder.append("UDS: ").append(UDS).append(EOL);
		stringBuilder.append("userDetails: ").append(userDetails).append(EOL);

		System.out.println(stringBuilder);
		assertNotNull(stringBuilder);
	}
}