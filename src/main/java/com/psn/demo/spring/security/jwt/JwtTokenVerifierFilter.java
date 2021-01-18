package com.psn.demo.spring.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.psn.demo.spring.security.config.JwtConfig;

import io.jsonwebtoken.JwtException;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {
	
	private JwtConfig jwtConfig;
	
	public JwtTokenVerifierFilter(JwtConfig jwtConfig) {
		super();
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		} else {
			String jwtToken = authHeader.replace("Bearer ", "");

			try {
				Authentication auth = JwtTokenizerUtil.parstJwtToken(jwtToken, jwtConfig);
				SecurityContextHolder.getContext().setAuthentication(auth);
				filterChain.doFilter(request, response);
			} catch (JwtException e) {
				throw new IllegalStateException("Token is invalid.", e);
			}

		}

	}

}
