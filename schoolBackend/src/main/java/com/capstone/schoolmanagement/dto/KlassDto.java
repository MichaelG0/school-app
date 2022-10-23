package com.capstone.schoolmanagement.dto;

import com.capstone.schoolmanagement.model.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlassDto {
	private Course course;
}
