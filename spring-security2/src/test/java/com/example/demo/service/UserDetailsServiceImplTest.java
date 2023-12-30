package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Authority;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {

	@Autowired
	SiteUserRepository repository;
	@Autowired
	UserDetailsServiceImpl service;

	@Test
	@DisplayName("ユーザ名が存在するとき、ユーザ詳細を取得することを期待します")
	void whenUsernameExists_expectToGetUserDetails() {
		// 準備
		SiteUser user = new SiteUser();
		String username = "Yamada";
		user.setUsername(username);
		user.setPassword("password");
		user.setEmail("yamada@example.com");
		user.setAuthority(Authority.USER);
		repository.save(user);

		// 実行
		UserDetails actual = service.loadUserByUsername(username);

		// 検証
		assertThat(actual.getUsername()).isEqualTo(user.getUsername());
//		assertEquals(user.getUsername(), actual.getUsername());
	}

	@Test
	@DisplayName("ユーザ名が存在しないとき、例外をスローします")
	void whenUsernameDoesNotExist_throwException() {
		// 実行と検証を同時にする
		assertThrows(UsernameNotFoundException.class,
				() -> service.loadUserByUsername("Yamazaki"));
	}
}
