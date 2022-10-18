package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.ECourse;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {

	public Optional<Course> findByName(String name);
	
	public Optional<List<Course>> findByType(ECourse type);
	
}
