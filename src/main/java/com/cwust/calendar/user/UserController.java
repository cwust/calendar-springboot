package com.cwust.calendar.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwust.calendar.user.dto.UserFullInfoDTO;
import com.cwust.calendar.user.dto.UserInfoDTO;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody UserFullInfoDTO dto) {
		userService.createNewUser(dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<UserInfoDTO> findUserByUsername(@PathVariable("username") String username) {
		UserInfoDTO user = userService.findUserByUsername(username);
		return ResponseEntity.ok(user);	
	}
	
}
