package com.capstone.schoolmanagement.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class StudentDto {
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	private String email;
	@NotBlank
	private String gender;
	@NotBlank
	private String address;
}
