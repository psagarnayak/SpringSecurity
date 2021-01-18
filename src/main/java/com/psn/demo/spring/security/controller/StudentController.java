package com.psn.demo.spring.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psn.demo.spring.security.model.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private List<Student> studentList = new ArrayList<>(Arrays.asList(new Student(1, "First Student"),
			new Student(2, "Second Student"), new Student(3, "Third Student"), new Student(4, "Fourth Student"),
			new Student(5, "Fifth Student")));

	// PreAuthorize can be used instead of specifying ant matchers in Security Config.
	// First add: @EnableGlobalMethodSecurity(prePostEnabled=true) to SecurityConfi class
	// Then specify PreAuth in any of the below formats
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	// @PreAuthorize("hasAnyRole('Role_CLERK', 'ROLE_ADMIN')")
	// @PreAuthorize("hasAuthority('student_read'))
	// @PreAuthorize("hasAuthority('student_add', 'student_update')")
	@GetMapping("")
	public List<Student> getAllStudents() {
		return this.studentList;
	}

	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable("id") long studentId) {
		return studentList.stream().filter(st -> st.getId() == studentId).findFirst().orElse(null);
	}

	@PostMapping("")
	public Student postNewStudent(@RequestBody Student addRequest) {

		if (addRequest != null) {

			addRequest.setId(studentList.size() + 1);
			studentList.add(addRequest);
		}
		return addRequest;
	}

	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable("id") long studentId, @RequestBody Student updateRequest) {

		Student student = studentList.stream().filter(st -> st.getId() == studentId).findFirst().orElse(null);

		if (student != null) {
			student.setName(updateRequest.getName());
		}

		return student;
	}

	@DeleteMapping("/{id}")
	public boolean deleteStudent(@PathVariable("id") long studentId) {

		int originalSize = this.studentList.size();

		this.studentList = studentList.stream().filter(st -> st.getId() != studentId).collect(Collectors.toList());

		return this.studentList.size() < originalSize;
	}

}
