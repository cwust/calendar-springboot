package com.cwust.calendar.auth.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.cwust.calendar.auth.CalendarUserDetails;

public class AuthenticationResponseDTO {
	private String username;
	private String jwtToken;
	private String fullName;
	private List<String> authorities;

	public static AuthenticationResponseDTO from(CalendarUserDetails userDetails, String jwtToken) {
		AuthenticationResponseDTO dto = new AuthenticationResponseDTO();
		
		dto.setUsername(userDetails.getUsername());
		dto.setFullName(userDetails.getFullName());
		dto.setAuthorities(userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList());
		dto.setJwtToken(jwtToken);
		return dto;
	}
	
	public AuthenticationResponseDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
}
