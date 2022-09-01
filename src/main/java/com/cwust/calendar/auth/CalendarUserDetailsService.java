package com.cwust.calendar.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cwust.calendar.base.exception.CalendarError;
import com.cwust.calendar.user.UserRepository;
import com.cwust.calendar.user.entity.User;

@Service
public class CalendarUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		
		if (user == null) {
			CalendarError.INVALID_LOGIN.throwException();
			return null;
		} else {
			return CalendarUserDetails.from(user);
		}
	}
}
