package com.cwust.calendar.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
	public boolean isAdmin() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
		return true;
	}
}
