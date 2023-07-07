package com.basics.testmore.configuration; //.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static com.basics.testmore.util.UtilityMain.EOL;

/*
	https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
	https://spring.io/guides/gs/securing-web/
*/
@Configuration @EnableWebSecurity
class SecurityConfiguration {

	private static final String USER_DEFAULT = "user";
	private static final String PASS_DEFAULT = "secret";
	private static final String ROLE_USER = "USER";
	private static final String ROLE_ADMIN = "ADMIN";

	@Bean public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

		DefaultSecurityFilterChain DSFC = null;
		try {
			httpSecurity
				.authorizeHttpRequests(requests -> requests
					.requestMatchers("/**").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin( form -> form
					.loginPage("/login")
					.permitAll()
				)
				.logout(LogoutConfigurer::permitAll) // .logout(logout -> logout.permitAll())
			;
			DSFC = httpSecurity.build();
		}
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		System.out.println( EOL + "SecurityFilterChain");
		return DSFC;
	}

	@Bean public UserDetailsService userDetailsService( ) {

		UserDetails user = User.builder()
			.username(USER_DEFAULT)
			.password(PASS_DEFAULT)
			.roles(ROLE_USER)
			.build();

		UserDetails admin = User.builder()
			.username("admin")
			.password("secret123")
			.roles(ROLE_USER, ROLE_ADMIN)
			.build();

		System.out.println( EOL + "UserDetailsService");
		UserDetailsService userDetailsService = new InMemoryUserDetailsManager(user, admin);

		return userDetailsService;
	}

	// the following can be commented out
	@Bean public WebSecurityCustomizer webSecurityCustomizer( ) {

		System.out.println( EOL + "WebSecurityCustomizer");
		WebSecurityCustomizer webSecurityCustomizer = web
			-> web.ignoring().requestMatchers("/ignore1", "/ignore2");

		return webSecurityCustomizer;
	}

	@Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {

		System.out.println( EOL + "AuthenticationManager");
		AuthenticationManager authenticationManager = null;
		try { authenticationManager = authConfig.getAuthenticationManager(); }
		catch (Exception ex) { System.out.println("ERROR: " + ex.getMessage()); }

		return authenticationManager;
	}

	@Bean public PasswordEncoder passwordEncoder( ) {

		System.out.println( EOL + "PasswordEncoder");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		return bCryptPasswordEncoder;
	}
}