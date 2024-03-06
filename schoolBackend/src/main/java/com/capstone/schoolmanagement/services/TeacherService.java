package com.capstone.schoolmanagement.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.TeacherResponse;
import com.capstone.schoolmanagement.model.users.Teacher;
import com.capstone.schoolmanagement.repos.TeacherRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {
	private final TeacherRepo tcrRepo;

	public List<Teacher> getAll() {
		return (List<Teacher>) tcrRepo.findAll();
	}

	public TeacherResponse getById(Long id) {
		Teacher teacher = tcrRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
		return TeacherResponse.buildTeacherResponse(teacher);
	}
}
