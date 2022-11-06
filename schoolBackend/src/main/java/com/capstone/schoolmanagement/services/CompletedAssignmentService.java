package com.capstone.schoolmanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		Optional<CompletedAssignment> complAssOpt = complAssRepo.findByStudentIdAndAssignmentId(dto.getStudentId(),
				dto.getAssignmentId());
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
						() -> new EntityNotFoundException("Completed Assignment not found by user id" + studentId));
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
		return ass.isPresent() ? CompletedAssignmentResponse.buildCompletedAssignmentResponse(ass.get())
				: new CompletedAssignmentResponse();
	}

	public Page<CompletedAssignmentResponse> getByKlassAndTeacherIds(Long klassId, Long teacherId,
			Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, "submittedDate");
		Page<CompletedAssignment> assPage = complAssRepo.findByKlassAndTeacherIds(klassId, teacherId, pgb)
				.orElseThrow(() -> new EntityNotFoundException("Assignments not found"));
		List<CompletedAssignmentResponse> assList = assPage.stream()
				.map(ass -> CompletedAssignmentResponse.buildCompletedAssignmentResponse(ass))
				.toList();
		return new PageImpl<CompletedAssignmentResponse>(assList, pgb, assPage.getTotalElements());
	}

	public CompletedAssignmentResponse assignGrade(Long id, float grade) {
		CompletedAssignment ass = complAssRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Completed Assignment not found"));
		ass.setGrade(grade);
		complAssRepo.save(ass);
		return CompletedAssignmentResponse.buildCompletedAssignmentResponse(ass);
	}

}
