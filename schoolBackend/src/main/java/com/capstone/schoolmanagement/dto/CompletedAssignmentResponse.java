package com.capstone.schoolmanagement.dto;

import java.time.LocalDate;

import com.capstone.schoolmanagement.auth.users.UserBasicResponse;
import com.capstone.schoolmanagement.model.CompletedAssignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletedAssignmentResponse {
	private Long id;
	private String title;
	private LocalDate submittedDate;
	private UserBasicResponse student;
	private String link;
	private float grade;

	public static CompletedAssignmentResponse buildCompletedAssignmentResponse(CompletedAssignment assignment) {
		return CompletedAssignmentResponse.builder()
				.id(assignment.getId())
				.title(assignment.getAssignment().getTitle())
				.submittedDate(assignment.getSubmittedDate())
				.student(UserBasicResponse.buildBasicUserResponse(assignment.getStudent()))
				.link(assignment.getLink())
				.grade(assignment.getGrade())
				.build();
	}
	
}
