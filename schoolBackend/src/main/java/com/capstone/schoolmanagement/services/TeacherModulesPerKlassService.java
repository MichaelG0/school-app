package com.capstone.schoolmanagement.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.TeacherModulesPerKlassResponse;
import com.capstone.schoolmanagement.model.TeacherModulesPerKlass;
import com.capstone.schoolmanagement.repos.TeacherModulesPerKlassRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherModulesPerKlassService {
	private final TeacherModulesPerKlassRepo tcrMPKRepo;

	public List<TeacherModulesPerKlassResponse> getByTeacherId(Long teacherId) {
		List<TeacherModulesPerKlass> teacherMPKList = tcrMPKRepo.findByTeacherId(teacherId)
				.orElseThrow(() -> new EntityNotFoundException("TeacherModulesPerKlassResponse not found"));
		return teacherMPKList.stream().map(tcrMPK -> TeacherModulesPerKlassResponse.buildResponse(tcrMPK)).toList();
	}
}
