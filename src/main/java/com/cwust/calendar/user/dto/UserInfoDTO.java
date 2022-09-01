package com.cwust.calendar.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cwust.calendar.user.validation.UserLoginConstraint;

public class UserInfoDTO {
	@NotEmpty
	@Length(min = 6, max = 20, message = "Username must have between 6 and 20 characters")
	@UserLoginConstraint
	private String username;

	@NotEmpty
	private String fullName;

	@NotEmpty
	@Email
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
