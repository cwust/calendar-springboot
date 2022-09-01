package com.cwust.calendar.user;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwust.calendar.auth.CurrentUser;
import com.cwust.calendar.auth.PasswordUtils;
import com.cwust.calendar.base.exception.CalendarError;
import com.cwust.calendar.user.dto.UserFullInfoDTO;
import com.cwust.calendar.user.dto.UserInfoDTO;
import com.cwust.calendar.user.entity.User;
import com.cwust.calendar.user.entity.UserAuthorities;

@Service
public class UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordUtils passwordUtils;
	
	@Autowired
	private CurrentUser currentUser;
	
	public void createNewUser(UserFullInfoDTO dto) {
		LOG.info("Saving user {}", dto.getUsername());
		
		this.validateNewUser(dto);
		
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setFullName(dto.getFullName());
		user.setEmail(dto.getEmail());
		
		user.setPassword(passwordUtils.encode(dto.getPassword()));
		
		user.setAuthorities(dto.getAuthorities().stream()
				.map(UserAuthorities::new)
				.collect(Collectors.toList())
		);
		
		this.userRepository.save(user);
	}
	
	void validateNewUser(UserFullInfoDTO dto) {
		if (!this.currentUser.isAdmin()) {
			CalendarError.UNAUTHORIZED.throwException();
		}
		
		User user = this.userRepository.findByUsername(dto.getUsername());
		
		if (user != null) {
			CalendarError.LOGIN_UNAVAILABLE.throwException();
		}
	}
	
	public UserInfoDTO findUserByUsername(String username) {
		
		this.validateFindUserByUsername(username);
		
		User user = this.userRepository.findByUsername(username);
		
		if (user == null) {
			return null;
		}
		
		UserInfoDTO dto = new UserInfoDTO();
		dto.setUsername(user.getUsername());
		dto.setFullName(user.getFullName());
		dto.setEmail(user.getEmail());
		
		return dto;
	}
	
	void validateFindUserByUsername(String username) {
		if (!this.currentUser.getUsername().equals(username) &&
			!this.currentUser.isAdmin()) {
			CalendarError.UNAUTHORIZED.throwException();
		}
				
	}
}
