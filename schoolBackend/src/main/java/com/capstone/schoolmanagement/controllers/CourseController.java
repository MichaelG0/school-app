package com.capstone.schoolmanagement.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capstone.schoolmanagement.dto.CourseDto;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.services.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController implements IControllerList<Course, CourseDto> {
	private final CourseService crsSrv;
	
	@Override
	@PostMapping
	public ResponseEntity<Course> create(@RequestBody CourseDto dto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(crsSrv.create(dto));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<Course>> getAll() {
		return ResponseEntity.ok(crsSrv.getAll());
	}
	
	@GetMapping("/upcoming")
	public ResponseEntity<List<Course>> getAllUpcoming(){
		return ResponseEntity.ok(crsSrv.getAllUpcoming());
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Course> getById(@PathVariable Long id) {
		return ResponseEntity.ok(crsSrv.getById(id));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody CourseDto dto) {
		return ResponseEntity.ok(crsSrv.update(id, dto));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		crsSrv.delete(id);
		return ResponseEntity.ok().build();
	}

}
