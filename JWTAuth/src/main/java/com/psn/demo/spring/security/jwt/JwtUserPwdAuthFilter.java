package com.psn.demo.spring.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psn.demo.spring.security.config.JwtConfig;

/**
 * Filter is automatically invoked for POST request to url pattern /login Filter
 * expects username and password to be provided as body of the request.
 * 
 */
public class JwtUserPwdAuthFilter extends UsernamePasswordAuthenticationFilter {

	private JwtConfig jwtConfig;
	
	public JwtUserPwdAuthFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		super(authenticationManager);
		this.jwtConfig = jwtConfig;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		ObjectMapper mapper = new ObjectMapper();
		LoginRequest loginReq = null;
		try {
			loginReq = mapper.readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BadCredentialsException("Error parsing login request!", e);
		}

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginReq.getUsername(),
				loginReq.getPassword());

		return getAuthenticationManager().authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Refer https://github.com/jwtk/jjwt

		response.addHeader("Authorization", "Bearer " + JwtTokenizerUtil.buildJwtToken(authResult, jwtConfig));
	}

}
