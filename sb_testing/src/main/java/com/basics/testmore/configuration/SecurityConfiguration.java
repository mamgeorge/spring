package com.basics.testmore.configuration; //.configuration;

import com.basics.testmore.TestmoreApp;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.logging.Logger;

import static com.basics.testmore.util.UtilityMain.EOL;

/*
	https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
	https://spring.io/guides/gs/securing-web/
*/
@Configuration @EnableWebSecurity
class SecurityConfiguration {

	private static final Logger LOGGER = Logger.getLogger(SecurityConfiguration.class.getName());
	private static final String USER_DEFAULT = "user";
	private static final String PASS_DEFAULT = "secret";
	private static final String ROLE_USER = "USER";
	private static final String ROLE_ADMIN = "ADMIN";

	@Bean public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

		DefaultSecurityFilterChain DSFC = null;
		AntPathRequestMatcher APRM = new AntPathRequestMatcher("/**");
		try {
			httpSecurity
				.authorizeHttpRequests(requests -> requests
					.requestMatchers(APRM).permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(form -> form
					.loginPage("/login")
					.permitAll()
				)
				.logout(LogoutConfigurer::permitAll) // .logout(logout -> logout.permitAll())
			;
			DSFC = httpSecurity.build();
		}
		catch (Exception ex) { LOGGER.severe("ERROR: " + ex.getMessage()); }

		LOGGER.info(EOL + "SecurityFilterChain");
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

		LOGGER.info(EOL + "UserDetailsService");
		UserDetailsService userDetailsService = new InMemoryUserDetailsManager(user, admin);

		return userDetailsService;
	}

	// the following can be commented out
	@Bean public WebSecurityCustomizer webSecurityCustomizer( ) {

		LOGGER.info(EOL + "WebSecurityCustomizer");
		RequestMatcher requestMatcher = new AntPathRequestMatcher("/ignore1", "GET");
		WebSecurityCustomizer webSecurityCustomizer = web
			-> web.ignoring().requestMatchers(requestMatcher);

		return webSecurityCustomizer;
	}

	@Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {

		LOGGER.info(EOL + "AuthenticationManager");
		AuthenticationManager authenticationManager = null;
		try { authenticationManager = authConfig.getAuthenticationManager(); }
		catch (Exception ex) { LOGGER.severe("ERROR: " + ex.getMessage()); }

		return authenticationManager;
	}

	@Bean public PasswordEncoder passwordEncoder( ) {

		LOGGER.info(EOL + "PasswordEncoder");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		return bCryptPasswordEncoder;
	}
}