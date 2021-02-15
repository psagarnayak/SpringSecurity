package com.psn.demo.spring.security.config;

public enum UserAuthorities {
	
	STUDENT_READ("student_read"),
	STUDENT_ADD("student_add"),
	STUDENT_UPDATE("student_update"),
	STUDENT_DELETE("student_delete");
	
	private final String authority;
	
	private UserAuthorities(String authority) {
		this.authority = authority;
	}
	
	public String getAuthority() {
		return this.authority;
	}
}
