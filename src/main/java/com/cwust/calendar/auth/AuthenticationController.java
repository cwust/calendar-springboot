package com.cwust.calendar.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwust.calendar.auth.dto.AuthenticationDTO;
import com.cwust.calendar.auth.dto.AuthenticationResponseDTO;
import com.cwust.calendar.auth.jwt.JwtTokenUtils;
import com.cwust.calendar.base.AbstractCalendarController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController extends AbstractCalendarController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@PostMapping
	public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationDTO dto,
			BindingResult result) throws AuthenticationException {
		this.validateBindingResult(result);

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				dto.getUsername(), dto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		CalendarUserDetails userDetails = (CalendarUserDetails) authentication.getPrincipal();
		String token = jwtTokenUtils.createToken(userDetails);
		AuthenticationResponseDTO response = AuthenticationResponseDTO.from(userDetails, token);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path = "/ping")
	public ResponseEntity<AuthenticationResponseDTO> ping() {
		return ResponseEntity.ok().build();
	}
}
