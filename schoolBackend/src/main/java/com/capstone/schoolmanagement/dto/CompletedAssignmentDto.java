package com.capstone.schoolmanagement.dto;

import lombok.Data;

@Data
public class CompletedAssignmentDto {
	private Long studentId;
	private String link;
	private Long assignmentId;
}
