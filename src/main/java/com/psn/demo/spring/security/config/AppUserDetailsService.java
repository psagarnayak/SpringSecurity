package com.psn.demo.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.psn.demo.spring.security.repo.UserRepo;

@Component
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("fakeRepo")
	private UserRepo userRepo;

	public AppUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepo.fetchUserDetails(username);
	}

}
