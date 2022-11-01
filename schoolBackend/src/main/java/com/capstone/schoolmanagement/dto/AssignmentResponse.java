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
	private String dueDate;
	private String title;
	private String caption;
	private Long klassId;
	private String module;

	public static AssignmentResponse buildAssignmentResponse(Assignment assignment) {
		LocalDate dueDate = assignment.getDueDate();
		
		return AssignmentResponse.builder()
				.id(assignment.getId())
				.issueDate(assignment.getIssueDate())
				.dueDate(dueDate.isBefore(LocalDate.now()) ? "Expired" : dueDate.toString())
				.title(assignment.getTitle())
				.caption(assignment.getCaption())
				.klassId(assignment.getKlass().getId())
				.module(assignment.getModule().getName())
				.build();
	}
}
