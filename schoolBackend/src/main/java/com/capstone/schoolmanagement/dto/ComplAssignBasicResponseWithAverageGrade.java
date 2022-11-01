package com.capstone.schoolmanagement.dto;

import java.util.List;

import com.capstone.schoolmanagement.model.CompletedAssignment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplAssignBasicResponseWithAverageGrade {
	private int averageGrade;
	private List<CompletedAssignmentBasicResponse> completedAssignments;
	
	public static ComplAssignBasicResponseWithAverageGrade buildResponse(int averageGrade, List<CompletedAssignment> assignments) {
		return ComplAssignBasicResponseWithAverageGrade.builder()
				.averageGrade(averageGrade)
				.completedAssignments(assignments.stream().map(ass -> CompletedAssignmentBasicResponse.buildResponse(ass)).toList())
				.build();
	}
	
}
