package com.psn.demo.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Note: Order of ant matchers is important,
		// If any any matcher pattern (Http Method, url pattern) match and the auth
		// check fails,
		// System will block the request even if there exists a subsequent matching
		// pattern with
		// a passing auth check

		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/students/**").hasAuthority(UserAuthorities.STUDENT_READ.getAuthority())
		.antMatchers(HttpMethod.POST, "/api/students/**").hasAuthority(UserAuthorities.STUDENT_ADD.getAuthority())
		.antMatchers(HttpMethod.PUT, "/api/students/**").hasAuthority(UserAuthorities.STUDENT_UPDATE.getAuthority())
		.antMatchers(HttpMethod.DELETE, "/api/students/**").hasRole(UserRoles.ADMIN.name()).anyRequest().authenticated().and().httpBasic();
	}

}
