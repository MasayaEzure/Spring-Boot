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
		// リクエストを実行
		.perform(
				post("/register")  // HTTPのPOSTリクエストを使用する
				.flashAttr("user", new SiteUser())  // 入力の属性を設定
				.with(csrf())  // CSRFトークンを自動挿入する
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
		.andExpect(model().hasNoErrors())  // エラーがないことを検証
		.andExpect(redirectedUrl("/login?register"))  //　指定したURLにリダイレクトするかを検証
		.andExpect(status().isFound());  // ステータスコード 302 の検証
	}

	@Test
	@DisplayName("管理者ユーザでログイン時、ユーザ一覧を表示することを期待します")
	@WithMockUser(username="admin", authorities="ADMIN")
	void whenLoggedInAsAdminUser_expectToSeeListOfUsers() throws Exception {
		mockMvc.perform(get("/admin/list"))
		.andExpect(status().isOk())  // ステータスコード 200 の検証
		.andExpect(content().string(containsString("ユーザ一覧")))  // HTML の表示内容に、指定した文字列を含むかの検証
		.andExpect(view().name("list"));  // 指定した HTML を表示
	}
}
