package com.cwust.calendar.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.cwust.calendar.auth.CurrentUser;
import com.cwust.calendar.auth.PasswordUtils;
import com.cwust.calendar.base.exception.CalendarError;
import com.cwust.calendar.base.exception.CalendarException;
import com.cwust.calendar.user.dto.UserFullInfoDTO;
import com.cwust.calendar.user.dto.UserInfoDTO;
import com.cwust.calendar.user.entity.User;
import com.cwust.calendar.user.entity.UserAuthorities;
import com.cwust.calendar.user.enums.CalendarAuthority;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

	@MockBean
	private CurrentUser currentUser;
	
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordUtils passwordUtils;

	@Autowired
	private UserService userService;
	
	@Test
	public void testfindUserByUsernameSameUser() {
		String userUsername = "charles";
		String userEmail = "charles@charles.com";
		String userFullName = "Charles Wust";
		
		User user = new User();
		user.setUsername(userUsername);
		user.setFullName(userFullName);
		user.setEmail(userEmail);
		
		doReturn(user).when(userRepository).findByUsername(userUsername);
		doReturn(userUsername).when(currentUser).getUsername();
		
		UserInfoDTO userInfo = this.userService.findUserByUsername(userUsername);
		
		assertNotNull(userInfo);
		assertEquals(userUsername, userInfo.getUsername());
		assertEquals(userFullName, userInfo.getFullName());
		assertEquals(userEmail, userInfo.getEmail());
	}
	
	@Test
	public void testfindUserByUsernameOtherUserAdmin() {
		String userUsername = "charles";
		String userEmail = "charles@charles.com";
		String userFullName = "Charles Wust";

		String currentuserUsername = "admin";

		User user = new User();
		user.setUsername(userUsername);
		user.setFullName(userFullName);
		user.setEmail(userEmail);
		
		doReturn(user).when(userRepository).findByUsername(userUsername);
		doReturn(currentuserUsername).when(currentUser).getUsername();
		doReturn(true).when(currentUser).isAdmin();
		
		UserInfoDTO userInfo = this.userService.findUserByUsername(userUsername);
		
		assertNotNull(userInfo);
		assertEquals(userUsername, userInfo.getUsername());
		assertEquals(userFullName, userInfo.getFullName());
		assertEquals(userEmail, userInfo.getEmail());
	}
	
	@Test
	public void testfindUserByUsernameOtherUserNotAdmin() {
		String userUsername = "charles";
		String userEmail = "charles@charles.com";
		String userFullName = "Charles Wust";

		String currentuserUsername = "another";

		User user = new User();
		user.setUsername(userUsername);
		user.setFullName(userFullName);
		user.setEmail(userEmail);
		
		doReturn(user).when(userRepository).findByUsername(userUsername);
		doReturn(currentuserUsername).when(currentUser).getUsername();
		doReturn(false).when(currentUser).isAdmin();
		
		try {
			this.userService.findUserByUsername(userUsername);
			fail();
		} catch (CalendarException ex) {
			assertEquals(CalendarError.UNAUTHORIZED, ex.getCalendarError());
		}
	}
	
	@Test
	public void testCreateUserOk() {
		String userUsername = "charles";
		String userPassword = "charles@1";
		String userEmail = "charles@charles.com";
		String userFullName = "Charles Wust";

		String userPasswordHash = "xxxxxxxxxxxxxxxxcxxxxxxhaxxxrles@1";
		
		String currentuserUsername = "admin";

		doReturn(null).when(userRepository).findByUsername(userUsername);
		
		doReturn(currentuserUsername).when(currentUser).getUsername();
		doReturn(true).when(currentUser).isAdmin();
		doReturn(userPasswordHash).when(passwordUtils).encode(userPassword);
		
		UserFullInfoDTO dto = new UserFullInfoDTO();
		dto.setUsername(userUsername);
		dto.setFullName(userFullName);
		dto.setEmail(userEmail);
		dto.setPassword(userPassword);
		dto.setAuthorities(Arrays.asList("USER"));
		
		this.userService.createNewUser(dto);
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(userRepository).save(userCaptor.capture());
		
		User savedUser = userCaptor.getValue();
		
		assertEquals(userUsername, savedUser.getUsername());
		assertEquals(userPasswordHash, savedUser.getPassword());
		assertEquals(userFullName, savedUser.getFullName());
		assertEquals(userEmail, savedUser.getEmail());
		assertEquals(Arrays.asList(CalendarAuthority.USER), savedUser.getAuthorities().stream().map(UserAuthorities::getAuthority).toList());
	}
	
	@Test
	public void testCreateUserUnauthorized() {
		String userUsername = "charles";
		String userPassword = "charles@1";
		String userEmail = "charles@charles.com";
		String userFullName = "Charles Wust";

		String userPasswordHash = "xxxxxxxxxxxxxxxxcxxxxxxhaxxxrles@1";
		
		String currentuserUsername = "someone";

		doReturn(null).when(userRepository).findByUsername(userUsername);
		
		doReturn(currentuserUsername).when(currentUser).getUsername();
		doReturn(false).when(currentUser).isAdmin();
		doReturn(userPasswordHash).when(passwordUtils).encode(userPassword);
		
		UserFullInfoDTO dto = new UserFullInfoDTO();
		dto.setUsername(userUsername);
		dto.setFullName(userFullName);
		dto.setEmail(userEmail);
		dto.setPassword(userPassword);
		dto.setAuthorities(Arrays.asList("USER"));
		
		try {
			this.userService.createNewUser(dto);
			fail();
		} catch (CalendarException e) {
			assertEquals(CalendarError.UNAUTHORIZED, e.getCalendarError());
		}
	}
	
	@Test
	public void testCreateuserUsernameUnavailable() {
		String userUsername = "charles";
		String userPassword = "charles@1";
		String userEmail = "charles@charles.com";
		String userFullName = "Charles Wust";

		String userPasswordHash = "xxxxxxxxxxxxxxxxcxxxxxxhaxxxrles@1";
		
		String currentuserUsername = "someone";

		doReturn(new User()).when(userRepository).findByUsername(userUsername);
		
		doReturn(currentuserUsername).when(currentUser).getUsername();
		doReturn(true).when(currentUser).isAdmin();
		doReturn(userPasswordHash).when(passwordUtils).encode(userPassword);
		
		UserFullInfoDTO dto = new UserFullInfoDTO();
		dto.setUsername(userUsername);
		dto.setFullName(userFullName);
		dto.setEmail(userEmail);
		dto.setPassword(userPassword);
		dto.setAuthorities(Arrays.asList("USER"));
		
		try {
			this.userService.createNewUser(dto);
			fail();
		} catch (CalendarException e) {
			assertEquals(CalendarError.LOGIN_UNAVAILABLE, e.getCalendarError());
		}
	}
}

