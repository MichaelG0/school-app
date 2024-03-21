package com.capstone.schoolmanagement.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.schoolmanagement.responses.TeacherModulesPerKlassResponse;
import com.capstone.schoolmanagement.services.TeacherModulesPerKlassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teacher_mpk")
@RequiredArgsConstructor
public class TeacherModulesPerKlassController {
	private final TeacherModulesPerKlassService tcrSrv;
	
	@GetMapping("{teacherId}")
	public ResponseEntity<List<TeacherModulesPerKlassResponse>> getByTeacherId(@PathVariable Long teacherId) {
		return ResponseEntity.ok(tcrSrv.getByTeacherId(teacherId));
	}
	
	@GetMapping("{teacherId}/{klassId}")
	public ResponseEntity<TeacherModulesPerKlassResponse> getByTeacherAndKlassIds(@PathVariable Long teacherId, @PathVariable Long klassId) {
		return ResponseEntity.ok(tcrSrv.getByTeacherAndKlassIds(teacherId, klassId));
	}
	
}
