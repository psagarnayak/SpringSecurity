package com.psn.demo.spring.security.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum UserRoles {

	STUDENT(buildStudentAuthorities()),
	CLERK(buildClerkAuthorities()),
	ADMIN(buildAdminAuthorities());

	private final Set<UserAuthorities> authorities;
	
	private UserRoles(Set<UserAuthorities> authorities) {
		Collections.emptySet();
		this.authorities = authorities;
	}
	
	private static Set<UserAuthorities> buildStudentAuthorities(){
		Set<UserAuthorities> authorities = new HashSet<>();
		authorities.add(UserAuthorities.STUDENT_READ);
		return authorities;
	}
	
	private static Set<UserAuthorities> buildClerkAuthorities(){
		Set<UserAuthorities> authorities = new HashSet<>();
		authorities.add(UserAuthorities.STUDENT_READ);
		authorities.add(UserAuthorities.STUDENT_ADD);
		authorities.add(UserAuthorities.STUDENT_UPDATE);
		return authorities;
	}
	
	private static Set<UserAuthorities> buildAdminAuthorities(){
		Set<UserAuthorities> authorities = new HashSet<>();
		authorities.add(UserAuthorities.STUDENT_READ);
		authorities.add(UserAuthorities.STUDENT_ADD);
		authorities.add(UserAuthorities.STUDENT_UPDATE);
		authorities.add(UserAuthorities.STUDENT_DELETE);
		return authorities;
	}
	
	public Set<UserAuthorities> getAuthorities(){
		return new HashSet<>(this.authorities);
	}
}
