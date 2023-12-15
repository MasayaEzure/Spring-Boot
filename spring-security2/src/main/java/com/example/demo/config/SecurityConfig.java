package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final SiteUserRepository userRepository;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
				// css, js, img などの静的リソースのアクセスを可能にする
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.anyRequest().authenticated()
				)
		.formLogin(login -> login
				.loginPage("/login") // ログイン時のURL
				.defaultSuccessUrl("/") // 認証後にリダイレクトする場所
				.permitAll()
				)
		// ログアウトの設定
		.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウト時のURL
				.permitAll()
				)
		.rememberMe(); // ブラウザを閉じて再度閉じた場合でも、ログインの状態を保持できる
		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			// ユーザ名の検索
			SiteUser user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
			// ユーザ情報の返却
			return new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("ADMIN"));
		};
	}
}
