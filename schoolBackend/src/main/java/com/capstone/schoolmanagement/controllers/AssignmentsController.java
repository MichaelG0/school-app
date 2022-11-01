package com.capstone.schoolmanagement.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.schoolmanagement.dto.AssignmentDto;
import com.capstone.schoolmanagement.dto.AssignmentResponse;
import com.capstone.schoolmanagement.services.AssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentsController implements IControllerPage<AssignmentResponse, AssignmentDto> {
	private final AssignmentService assSrv;

	@Override
	public ResponseEntity<?> create(AssignmentDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Page<AssignmentResponse>> getAll(Optional<Integer> page, Optional<Integer> size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<AssignmentResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(assSrv.getById(id));
	}
	
	@GetMapping("/class/{klassId}")
	public ResponseEntity<Page<AssignmentResponse>> getByKlassId(@PathVariable Long klassId,
			@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(assSrv.getByKlassId(klassId, page, size));
	}

	@Override
	public ResponseEntity<AssignmentResponse> update(Long id, AssignmentDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
