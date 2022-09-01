package com.cwust.calendar.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	
	@Autowired
	private JwtTokenUtils jwtTokenParser;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
        String username = getUsernameFromValidToken(request);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        	if (userDetails != null) {
        		setAuthenticationForUserDetails(userDetails, request);
        	}
        }
        
        chain.doFilter(request, response);
    }
	
	private String getUsernameFromValidToken(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        
        if (token == null) {
        	return null;
        }
        
        if (token.startsWith(BEARER_PREFIX)) {
        	token = token.substring(BEARER_PREFIX.length());
        }
        
        if (!jwtTokenParser.isTokenStillValid(token)) {
        	return null;
        }
        
        return jwtTokenParser.getUserUsernameFromToken(token);
	}	
	
	private void setAuthenticationForUserDetails(UserDetails userDetails, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
