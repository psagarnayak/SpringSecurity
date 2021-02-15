package com.psn.demo.spring.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.psn.demo.spring.security.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenizerUtil {

	public static String buildJwtToken(Authentication auth, JwtConfig jwtConfig) {
		return Jwts.builder().setSubject(auth.getName()).claim("authorities", auth.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(Date.from(LocalDateTime.now().plusDays(jwtConfig.getTokenValidityInDays()).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes())).compact();
	}

	public static Authentication parstJwtToken(String jwtToken, JwtConfig jwtConfig) {

		Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes())).build()
				.parseClaimsJws(jwtToken);
		Claims claims = jws.getBody();
		String userName = claims.getSubject();
		var authorities = (List<Map<String, String>>) claims.get("authorities");
		Set<SimpleGrantedAuthority> grantedAuths = authorities.stream()
				.map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());

		return new UsernamePasswordAuthenticationToken(userName, null, grantedAuths);

	}
}
