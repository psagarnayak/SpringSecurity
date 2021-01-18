package com.psn.demo.spring.security.model;

public class Student {
	
	private long id; 
	private String name;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(String name) {
		this.name = name;
	}
	
	public Student(long id, String name) {
		this.id = id;
		this.name = name;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
