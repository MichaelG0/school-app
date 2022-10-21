package com.capstone.schoolmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.capstone.schoolmanagement.model.Mmodule;

@Configuration
public class MmoduleConfig {

	@Bean
	@Scope("prototype")
	public Mmodule module() {
		return new Mmodule();
	}
	
}
