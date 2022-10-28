package com.capstone.schoolmanagement.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.schoolmanagement.dto.AssignmentResponse;
import com.capstone.schoolmanagement.services.AssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentsController {
	private final AssignmentService assSrv;

	@GetMapping("/class/{klassId}")
	public ResponseEntity<Page<AssignmentResponse>> getByKlassId(@PathVariable Long klassId,
			@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(assSrv.getByKlassId(klassId, page, size));
	}

}
