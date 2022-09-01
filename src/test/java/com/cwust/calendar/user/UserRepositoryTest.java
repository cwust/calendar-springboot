package com.cwust.calendar.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.cwust.calendar.user.entity.User;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindUserByLogin() {
		User user = this.userRepository.findByUsername("admin");
		
		assertEquals("admin", user.getUsername());
		assertEquals("System Administrator", user.getFullName());
		assertEquals("admin@admin.com", user.getEmail());
		assertNotNull(user.getCreateDate());
		assertNull(user.getDeleteDate());
	}

}
