package com.capstone.schoolmanagement.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IControllerList<T1, T2> {
	public ResponseEntity<T1> create(@RequestBody T2 dto);
	public ResponseEntity<List<T1>> getAll();
	public ResponseEntity<T1> getById(@PathVariable Long id);
	public ResponseEntity<T1> update(@PathVariable Long id, @RequestBody T2 dto);
	public ResponseEntity<Void> delete(@PathVariable Long id);
}
