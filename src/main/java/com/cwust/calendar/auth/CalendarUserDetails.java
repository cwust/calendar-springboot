package com.cwust.calendar.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cwust.calendar.user.entity.User;

public class CalendarUserDetails implements UserDetails {

	private static final long serialVersionUID = -7437333520191583235L;

	private String username;
	private String password;
	private String fullName;
	private List<SimpleGrantedAuthority> authorities;
	
	public static CalendarUserDetails from(User user) {
		CalendarUserDetails userDetails = new CalendarUserDetails();
		userDetails.username = user.getUsername();
		userDetails.password = user.getPassword();
		userDetails.authorities = user.getAuthorities().stream()
				.map(userAuthority -> new SimpleGrantedAuthority(userAuthority.getAuthority().toString()))
				.toList();
		
		return userDetails;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
