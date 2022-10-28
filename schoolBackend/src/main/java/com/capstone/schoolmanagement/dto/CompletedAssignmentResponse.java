package com.capstone.schoolmanagement.dto;

import java.time.LocalDate;

import com.capstone.schoolmanagement.auth.users.UserBasicResponse;
import com.capstone.schoolmanagement.model.Assignment;
import com.capstone.schoolmanagement.model.CompletedAssignment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompletedAssignmentResponse {
	private Long id;
	private LocalDate submittedDate;
	private UserBasicResponse student;
	private byte[] file;
	private int grade;

	public static CompletedAssignmentResponse buildCompletedAssignmentResponse(CompletedAssignment assignment) {
		return CompletedAssignmentResponse.builder()
				.id(assignment.getId())
				.submittedDate(assignment.getSubmittedDate())
				.student(UserBasicResponse.buildBasicUserResponse(assignment.getStudent()))
				.file(assignment.getFile())
				.grade(assignment.getGrade())
				.build();
	}
}
