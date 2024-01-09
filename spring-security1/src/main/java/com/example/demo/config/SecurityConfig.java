package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated()
				)
		.formLogin();
		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		UserBuilder users = User.builder().passwordEncoder(passwordEncoder()::encode);

		UserDetails user = users.username("user")
				.password("password")
				.authorities("USER")
				.build();

		UserDetails admin = users.username("admin")
				.password("password")
				.authorities("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}
}
