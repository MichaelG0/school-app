package com.capstone.schoolmanagement.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capstone.schoolmanagement.dto.RegisterDto;
import com.capstone.schoolmanagement.dto.RegisterResponse;
import com.capstone.schoolmanagement.model.Register;
import com.capstone.schoolmanagement.services.RegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/class_register")
@RequiredArgsConstructor
public class RegisterController {
	private final RegisterService rgsSrv;

	@PostMapping
	public ResponseEntity<RegisterResponse> create(@RequestBody RegisterDto dto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(rgsSrv.create(dto));
	}

	@GetMapping("/{studentId}/{klassId}")
	public ResponseEntity<Integer> getStudentAttendancePercentage(@PathVariable Long studentId, @PathVariable Long klassId) {
		return ResponseEntity.ok(rgsSrv.getStudentAttendancePercentage(studentId, klassId));
	}

}
