package com.cwust.calendar.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cwust.calendar.auth.dto.AuthenticationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loginAdminValid() throws Exception {
		
		MvcResult loginResult = this.mockMvc.perform(
				post("/api/auth")
				.contentType(APPLICATION_JSON)
				.content(asJsonString(new AuthenticationDTO("admin", "admin@1"))))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.login").value("admin"))
		.andReturn();
		
		String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.jwtToken");
		
		this.mockMvc.perform(
				get("/api/auth/ping")
				.header("Authorization", "Bearer " + token))
		.andExpect(status().isOk());
		
		this.mockMvc.perform(
				get("/api/auth/ping"))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void loginAdminWrongPassword() throws Exception {
		
		this.mockMvc.perform(
				post("/api/auth")
				.contentType(APPLICATION_JSON)
				.content(asJsonString(new AuthenticationDTO("admin", "wrong"))))
		.andExpect(status().isUnauthorized());
		
		this.mockMvc.perform(
				get("/api/auth/ping")
				.header("Authorization", "Bearer invalidtoken"))
		.andExpect(status().isUnauthorized());
	}

	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
