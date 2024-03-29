package com.capstone.schoolmanagement.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.capstone.schoolmanagement.dto.KlassDto;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.responses.KlassResponse;
import com.capstone.schoolmanagement.services.KlassService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class KlassController implements IControllerPage<KlassResponse, KlassDto> {
	private final KlassService klsSrv;

	@Override
	@PostMapping
	public ResponseEntity<KlassResponse> create(@RequestBody KlassDto klsDto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		Klass klass = klsSrv.create(klsDto);
		return ResponseEntity.created(uri).body(KlassResponse.buildKlassResponse(klass));
	}

	@Override
	@GetMapping
	public ResponseEntity<Page<KlassResponse>> getAll(@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(klsSrv.getAll(page, size));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<KlassResponse> getById(@PathVariable Long id) {
		Klass klass = klsSrv.getById(id);
		return ResponseEntity.ok(KlassResponse.buildKlassResponse(klass));
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<KlassResponse> getByStudentId(@PathVariable Long id) {
		return ResponseEntity.ok(klsSrv.getByStudentId(id));
	}
	
	@GetMapping("/teacher/{id}")
	public ResponseEntity<List<KlassResponse>> getByTeacherId(@PathVariable Long id) {
		return ResponseEntity.ok(klsSrv.getByTeacherId(id));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<KlassResponse> update(@PathVariable Long id, @RequestBody KlassDto klsDto) {
		return ResponseEntity.ok(klsSrv.update(id, klsDto));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		klsSrv.delete(id);
		return ResponseEntity.ok().build();
	}

}
