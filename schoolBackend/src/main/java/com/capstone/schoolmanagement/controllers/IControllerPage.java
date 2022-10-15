package com.capstone.schoolmanagement.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IControllerPage<T1, T2> {
	public ResponseEntity<?> create(@RequestBody T2 dto);
	public ResponseEntity<Page<T1>> getAll(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size);
	public ResponseEntity<T1> getById(@PathVariable Long id);
	public ResponseEntity<T1> update(@PathVariable Long id, @RequestBody T2 dto);
	public ResponseEntity<Void> delete(@PathVariable Long id);
}
