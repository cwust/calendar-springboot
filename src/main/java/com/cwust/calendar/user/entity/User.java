package com.cwust.calendar.user.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.cwust.calendar.base.entity.GenericUpdateableEntity;

@Entity
@Table(name = "users")
public class User extends GenericUpdateableEntity {
	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "fullname", nullable = false)
	private String fullName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email")
	private String email;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@OrderBy("authority")
	private List<UserAuthorities> authorities;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserAuthorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<UserAuthorities> authorities) {
		this.authorities = authorities;
	}

}
