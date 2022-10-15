package com.capstone.schoolmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.capstone.schoolmanagement.model.Klass;

@Configuration
public class KlassConfig {

	@Bean
	@Scope("prototype")
	public Klass klass() {
		return new Klass();
	}
}
