package com.psn.demo.spring.security.repo;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.psn.demo.spring.security.config.UserRoles;

@Repository(value = "fakeRepo")
public class UserRepo {

	private List<UserDetails> userDetailsInDB = Arrays.asList(
			User.builder().username("anna").password("apwd").passwordEncoder(getPasswordEncoder()::encode)
					.authorities(UserRoles.STUDENT.getGrantedAuthorities()).build(),
			User.builder().username("mike").password("mpwd").passwordEncoder(getPasswordEncoder()::encode)
					.authorities(UserRoles.CLERK.getGrantedAuthorities()).build(),
			User.builder().username("wilson").password("wpwd").passwordEncoder(getPasswordEncoder()::encode)
					.authorities(UserRoles.CLERK.getGrantedAuthorities()).build(),
			User.builder().username("frank").password("fpwd").passwordEncoder(getPasswordEncoder()::encode)
					.authorities(UserRoles.ADMIN.getGrantedAuthorities()).build());

	public UserRepo() {
	}

	public UserDetails fetchUserDetails(String userName) {

		return userDetailsInDB.stream().filter(u -> u.getUsername().equals(userName)).findFirst().orElse(null);
	}

	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
