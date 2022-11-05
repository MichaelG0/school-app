package com.capstone.schoolmanagement.controllers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	@PostMapping
	public ResponseEntity<AssignmentResponse> create(@RequestBody @Valid AssignmentDto dto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(assSrv.create(dto));
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
	public ResponseEntity<Page<AssignmentResponse>> getUpcomingByKlassId(@PathVariable Long klassId,
			@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(assSrv.getUpcomingByKlassId(klassId, page, size));
	}

	@GetMapping("/class/{klassId}/{teacherId}")
	public ResponseEntity<Page<AssignmentResponse>> getUpcomingByKlassAndModuleIds(@PathVariable Long klassId,
			@PathVariable Long teacherId, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(assSrv.getUpcomingByKlassAndModuleIds(klassId, teacherId, page, size));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<AssignmentResponse> update(@PathVariable Long id, @RequestBody @Valid AssignmentDto dto) {
		return ResponseEntity.ok(assSrv.update(id, dto));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		assSrv.delete(id);
		return ResponseEntity.ok().build();
	}

}
