package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.TeacherModulesPerKlass;

@Repository
public interface TeacherModulesPerKlassRepo extends CrudRepository<TeacherModulesPerKlass, Long> {
	
	public Optional<List<TeacherModulesPerKlass>> findByTeacherId(Long teacherId);
	
	public Optional<TeacherModulesPerKlass> findByTeacherIdAndKlassId(Long teacherId, Long klassId);

}
