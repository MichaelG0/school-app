package com.capstone.schoolmanagement.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capstone.schoolmanagement.dto.CompletedAssignmentDto;
import com.capstone.schoolmanagement.responses.ComplAssignBasicResponseWithAverageGrade;
import com.capstone.schoolmanagement.responses.CompletedAssignmentResponse;
import com.capstone.schoolmanagement.services.CompletedAssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/completed_assignments")
@RequiredArgsConstructor
public class CompletedAssignmentController
		implements IControllerList<CompletedAssignmentResponse, CompletedAssignmentDto> {
	private final CompletedAssignmentService assSrv;

	@Override
	@PostMapping
	public ResponseEntity<CompletedAssignmentResponse> create(@RequestBody CompletedAssignmentDto dto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(assSrv.create(dto));
	}

	@Override
	public ResponseEntity<List<CompletedAssignmentResponse>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<CompletedAssignmentResponse> getById(@PathVariable Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<ComplAssignBasicResponseWithAverageGrade> getBasicByStudentId(@PathVariable Long studentId) {
		return ResponseEntity.ok(assSrv.getBasicByStudentId(studentId));
	}

	@GetMapping("/{studentId}/{assignmentId}")
	public ResponseEntity<CompletedAssignmentResponse> getByStudentAndAssignmentIds(@PathVariable Long studentId,
			@PathVariable Long assignmentId) {
		return ResponseEntity.ok(assSrv.getByStudentAndAssignmentIds(studentId, assignmentId));
	}

	@GetMapping("/class/{klassId}/{teacherId}")
	public ResponseEntity<Page<CompletedAssignmentResponse>> getByKlassAndTeacherIds(@PathVariable Long klassId,
			@PathVariable Long teacherId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(assSrv.getByKlassAndTeacherIds(klassId, teacherId, page, size));
	}

	@Override
	public ResponseEntity<CompletedAssignmentResponse> update(Long id, CompletedAssignmentDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping("/{id}/assess")
	public ResponseEntity<CompletedAssignmentResponse> assignGrade(@PathVariable Long id, @RequestBody float grade) {
		return ResponseEntity.ok(assSrv.assignGrade(id, grade));
	}

}
