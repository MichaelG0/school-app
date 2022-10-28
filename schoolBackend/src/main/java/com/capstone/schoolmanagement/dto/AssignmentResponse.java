package com.capstone.schoolmanagement.dto;

import java.time.LocalDate;
import java.util.List;

import com.capstone.schoolmanagement.model.Assignment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentResponse {
	private Long id;
	private LocalDate issueDate;
	private LocalDate dueDate;
	private String title;
	private String caption;
	private String module;
//	private List<CompletedAssignmentResponse> completedAssignments;

	public static AssignmentResponse buildAssignmentResponse(Assignment assignment) {
		return AssignmentResponse.builder()
				.id(assignment.getId())
				.issueDate(assignment.getIssueDate())
				.dueDate(assignment.getDueDate())
				.title(assignment.getTitle())
				.caption(assignment.getCaption())
				.module(assignment.getModule().getName())
//				.completedAssignments(assignment.getCompletedAssignments()
//						.stream()
//						.map(complAss -> CompletedAssignmentResponse.buildCompletedAssignmentResponse(complAss))
//						.toList())
				.build();
	}
}
