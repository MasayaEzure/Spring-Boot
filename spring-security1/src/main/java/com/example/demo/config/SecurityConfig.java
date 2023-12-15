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
		// 暗号化用に BCrypt を使用
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// 認証リクエストの設定
		.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated() // 認証するように設定
				)
		.formLogin(); // フォームベース認証の設定
		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		// パスワードの暗号化
		UserBuilder users = User.builder().passwordEncoder(passwordEncoder()::encode);
		// "user"を用意
		UserDetails user = users.username("user")
				.password("password")
				.authorities("USER")
				.build();
		// "admin"を用意
		UserDetails admin = users.username("admin")
				.password("password")
				.authorities("ADMIN")
				.build();

		// メモリ内認証を使用
		return new InMemoryUserDetailsManager(user, admin);
	}
}
