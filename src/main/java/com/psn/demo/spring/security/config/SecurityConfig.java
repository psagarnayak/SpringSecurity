package com.psn.demo.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.psn.demo.spring.security.jwt.JwtTokenVerifierFilter;
import com.psn.demo.spring.security.jwt.JwtUserPwdAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtConfig JwtConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Note: Order of ant matchers is important,
		// If any any matcher pattern (Http Method, url pattern) match and the auth
		// check fails, then system will block the request even if there exists a
		// subsequent matching pattern with a passing auth check

		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUserPwdAuthFilter(authenticationManager(), JwtConfig))
		.addFilterAfter(new JwtTokenVerifierFilter(JwtConfig), JwtUserPwdAuthFilter.class)
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/students/**").hasAuthority(UserAuthorities.STUDENT_READ.getAuthority())
		.antMatchers(HttpMethod.POST, "/api/students/**").hasAuthority(UserAuthorities.STUDENT_ADD.getAuthority())
		.antMatchers(HttpMethod.PUT, "/api/students/**").hasAuthority(UserAuthorities.STUDENT_UPDATE.getAuthority())
		.antMatchers(HttpMethod.DELETE, "/api/students/**").hasRole(UserRoles.ADMIN.name()).anyRequest().authenticated();
	}

}
