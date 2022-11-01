package com.capstone.schoolmanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.ComplAssignBasicResponseWithAverageGrade;
import com.capstone.schoolmanagement.dto.CompletedAssignmentDto;
import com.capstone.schoolmanagement.dto.CompletedAssignmentResponse;
import com.capstone.schoolmanagement.model.Assignment;
import com.capstone.schoolmanagement.model.CompletedAssignment;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.repos.AssignmentRepo;
import com.capstone.schoolmanagement.repos.CompletedAssignmentRepo;
import com.capstone.schoolmanagement.repos.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompletedAssignmentService {
	private final CompletedAssignmentRepo complAssRepo;
	private final StudentRepo stdRepo;
	private final AssignmentRepo assRepo;

	public CompletedAssignmentResponse create(CompletedAssignmentDto dto) {
		Optional<CompletedAssignment> complAssOpt = complAssRepo.findByStudentIdAndAssignmentId(dto.getStudentId(), dto.getAssignmentId());
		CompletedAssignment complAss = complAssOpt.isPresent() ? complAssOpt.get() : new CompletedAssignment();
		Student std = stdRepo.findById(dto.getStudentId())
				.orElseThrow(() -> new EntityNotFoundException("Student not found"));
		Assignment assignment = assRepo.findById(dto.getAssignmentId())
				.orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
		complAss.setLink(dto.getLink());
		complAss.setSubmittedDate(LocalDate.now());
		complAss.setStudent(std);
		complAss.setAssignment(assignment);
		complAssRepo.save(complAss);
		return CompletedAssignmentResponse.buildCompletedAssignmentResponse(complAss);
	}

	public ComplAssignBasicResponseWithAverageGrade getBasicByStudentId(Long studentId) {
		List<CompletedAssignment> asss = complAssRepo.findByStudentId(studentId)
				.orElseThrow(
						() -> new EntityNotFoundException("Completed assignment not found by user id" + studentId));

		OptionalDouble averageOpt = asss.stream()
				.map(ass -> ass.getGrade())
				.filter(grade -> grade >= 1)
				.mapToDouble(grade -> grade)
				.average();
		int average = (int) (averageOpt.orElse(0) * 10);

		return ComplAssignBasicResponseWithAverageGrade.buildResponse(average, asss);
	}

	public CompletedAssignmentResponse getByStudentAndAssignmentIds(Long studentId, Long assignmentId) {
		Optional<CompletedAssignment> ass = complAssRepo.findByStudentIdAndAssignmentId(studentId, assignmentId);
		return ass.isPresent() ? CompletedAssignmentResponse.buildCompletedAssignmentResponse(ass.get()) : new CompletedAssignmentResponse();
	}

}
