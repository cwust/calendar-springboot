package com.cwust.calendar.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PasswordUtils {
	
	public String encode(String str) {
		if (!StringUtils.hasText(str)) {
			throw new IllegalArgumentException("Cannot encode an empty string");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(str);
	}
}
