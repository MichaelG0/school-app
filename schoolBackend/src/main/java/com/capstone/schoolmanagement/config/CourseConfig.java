package com.capstone.schoolmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.capstone.schoolmanagement.model.Course;

@Configuration
public class CourseConfig {

	@Bean
	@Scope("prototype")
	public Course course() {
		return new Course();
	}
}
