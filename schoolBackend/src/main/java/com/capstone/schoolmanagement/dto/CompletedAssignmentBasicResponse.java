package com.capstone.schoolmanagement.dto;

import com.capstone.schoolmanagement.model.CompletedAssignment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompletedAssignmentBasicResponse {
	private Long assignmentId;
	private float grade;

	public static CompletedAssignmentBasicResponse buildResponse(CompletedAssignment assignment) {
		return CompletedAssignmentBasicResponse.builder()
				.assignmentId(assignment.getAssignment().getId())
				.grade(assignment.getGrade())
				.build();
	}

}