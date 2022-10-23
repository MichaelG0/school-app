package com.capstone.schoolmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.github.javafaker.Faker;

@Configuration
public class ModelConfig {
	
	@Bean
	public Faker faker() {
		return new Faker();
	}

	@Bean
	@Scope("prototype")
	public CourseInfo courseInfo() {
		return new CourseInfo();
	}
	
	@Bean
	@Scope("prototype")
	public Course course() {
		return new Course();
	}
	
	@Bean
	@Scope("prototype")
	public Klass klass() {
		return new Klass();
	}
	
	@Bean
	@Scope("prototype")
	public Mmodule module() {
		return new Mmodule();
	}
	
	@Bean
	@Scope("prototype")
	public WeeklyScheduleItem weeklyScheduleItem() {
		return new WeeklyScheduleItem();
	}
	
}
