package com.psn.demo.spring.security.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/api/students/**")
				.hasAuthority(UserAuthorities.STUDENT_READ.name()).antMatchers(HttpMethod.POST, "/api/students/**")
				.hasAuthority(UserAuthorities.STUDENT_ADD.name()).antMatchers(HttpMethod.PUT, "/api/students/**")
				.hasAuthority(UserAuthorities.STUDENT_UPDATE.name()).antMatchers(HttpMethod.DELETE, "/api/students/**")
				.hasAuthority(UserAuthorities.STUDENT_DELETE.name()).anyRequest().authenticated().and().httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		UserDetails annaUserDetails = User.builder().passwordEncoder(passwordEncoder::encode)
				.username("anna").password("apwd").roles(UserRoles.STUDENT.name())
				.authorities(UserRoles.STUDENT.getAuthorities().stream()
				.map(UserAuthorities::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
				.build();

		UserDetails mikeUserDetails = User.builder().passwordEncoder(passwordEncoder::encode)
				.username("mike").password("mpwd").roles(UserRoles.CLERK.name())
				.authorities(UserRoles.CLERK.getAuthorities().stream()
				.map(UserAuthorities::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
				.build();

		UserDetails wilsonUserDetails = User.builder().passwordEncoder(passwordEncoder::encode)
				.username("wilson").password("wpwd").roles(UserRoles.CLERK.name())
				.authorities(UserRoles.CLERK.getAuthorities().stream()
				.map(UserAuthorities::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
				.build();

		UserDetails frankUserDetails = User.builder().passwordEncoder(passwordEncoder::encode)
				.username("frank").password("fpwd").roles(UserRoles.ADMIN.name())
				.authorities(UserRoles.ADMIN.getAuthorities().stream()
				.map(UserAuthorities::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()))
				.build();

		return new InMemoryUserDetailsManager(annaUserDetails, mikeUserDetails, wilsonUserDetails, frankUserDetails);
	}

	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
