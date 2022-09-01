package com.cwust.calendar.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cwust.calendar.utils.DateUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils {
	private static final Logger LOG = LoggerFactory.getLogger(JwtTokenUtils.class);

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";
	
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;
	
	@Autowired
	private DateUtils dateUtils;

	private Date generateExpirationDate() {
		return dateUtils.addSeconds(dateUtils.dateNow(), expiration);
	}

	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private String createToken(Map<String, Object> claims) {
		return Jwts.builder().
				setClaims(claims)
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public String createToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			claims.put(CLAIM_KEY_ROLE, authority.getAuthority());
		}
		claims.put(CLAIM_KEY_CREATED, dateUtils.dateNow());

		return createToken(claims);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}
	
	public String getUserUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}
	
	public boolean isTokenStillValid(String token) {
		try {
			Date expirationDate = this.getExpirationDateFromToken(token);
			return expirationDate != null && expirationDate.after(dateUtils.dateNow());
		} catch (Exception e) {
			LOG.error("Error validating JWT token: ", e);
			return false;
		}
	}
}
