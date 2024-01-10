package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.SiteUser;
import com.example.demo.util.Authority;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("登録エラーがあるとき、エラー表示することを期待します")
	void whenThereIsRegistrationError_expectToSeeErrors() throws Exception {
		mockMvc
		.perform(
				post("/register")
				.flashAttr("user", new SiteUser())
				.with(csrf())
				)
				.andExpect(model().hasErrors())
				.andExpect(view().name("register"));
	}

	@Test
	@DisplayName("管理者ユーザとして登録するとき、成功することを期待します")
	void whenRegisteringAsAdminUser_expectToSucceed() throws Exception {
		SiteUser user = new SiteUser();
		user.setUsername("管理者ユーザ");
		user.setPassword("password");
		user.setEmail("admin@example.com");
		user.setGender(0);
		user.setAdmin(true);
		user.setAuthority(Authority.ADMIN);
	
		mockMvc.perform(post("/register")
				.flashAttr("user", user).with(csrf()))
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/login?register"))
		.andExpect(status().isFound());
	}

	@Test
	@DisplayName("管理者ユーザでログイン時、ユーザ一覧を表示することを期待します")
	@WithMockUser(username="admin", authorities="ADMIN")
	void whenLoggedInAsAdminUser_expectToSeeListOfUsers() throws Exception {
		mockMvc.perform(get("/admin/list"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("ユーザ一覧")))
		.andExpect(view().name("list"));
	}
}
