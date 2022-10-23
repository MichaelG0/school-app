package com.capstone.schoolmanagement.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capstone.schoolmanagement.dto.CourseInfoDto;
import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.services.CourseInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courseinfo")
@RequiredArgsConstructor
public class CourseInfoController implements IControllerList<CourseInfo, CourseInfoDto> {
	private final CourseInfoService crsSrv;

	@Override
	@PostMapping
	public ResponseEntity<CourseInfo> create(@RequestBody CourseInfoDto crsDto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(crsSrv.create(crsDto));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<CourseInfo>> getAll() {
		return ResponseEntity.ok(crsSrv.getAll());
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<CourseInfo>> getByType(@PathVariable String type) {
		return ResponseEntity.ok(crsSrv.getByType(type));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<CourseInfo> getById(@PathVariable Long id) {
		return ResponseEntity.ok(crsSrv.getById(id));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<CourseInfo> update(@PathVariable Long id, @RequestBody CourseInfoDto crsDto) {
		return ResponseEntity.ok(crsSrv.update(id, crsDto));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		crsSrv.delete(id);
		return ResponseEntity.ok().build();
	}

}
