package com.capstone.schoolmanagement.dto;

import java.util.List;

import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.users.Student;

import lombok.Data;

@Data
public class RegisterDto {
	private String date;
	private Long klassId;
	private List<Student> absent;
}
