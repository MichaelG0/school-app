package com.capstone.schoolmanagement.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.schoolmanagement.model.users.Teacher;
import com.capstone.schoolmanagement.responses.TeacherResponse;
import com.capstone.schoolmanagement.services.TeacherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
	private final TeacherService tcrSrv;

	@GetMapping
	public ResponseEntity<List<Teacher>> getAll() {
		return ResponseEntity.ok(tcrSrv.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TeacherResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(tcrSrv.getById(id));
	}
}
