package com.cwust.calendar.user.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cwust.calendar.user.validation.NoDuplicatesConstraint;
import com.cwust.calendar.user.validation.PasswordConstraint;
import com.cwust.calendar.user.validation.UserLoginConstraint;
import com.cwust.calendar.user.validation.ValidCalendarAuthorityConstraint;

public class UserFullInfoDTO {
	@NotEmpty
	@Length(min = 6, max = 20, message = "Username must have between 6 and 20 characters")
	@UserLoginConstraint
	private String username;

	@NotEmpty
	private String fullName;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Min(value = 6, message = "${password.minSize}")
	@PasswordConstraint
	private String password;

	@NotEmpty
	@NoDuplicatesConstraint(message = "${profile.noDuplicates}")
	@ValidCalendarAuthorityConstraint
	private List<String> authorities;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

}
