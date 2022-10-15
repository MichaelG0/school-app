package com.capstone.schoolmanagement.auth.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.model.users.Teacher;

@Configuration
public class UserConfig {

	@Bean
	@Scope("prototype")
	public Student student() {
		return new Student();
	}
	
	@Bean
	@Scope("prototype")
	public Teacher teacher() {
		return new Teacher();
	}
	
}
