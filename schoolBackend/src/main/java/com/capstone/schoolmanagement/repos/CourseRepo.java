package com.capstone.schoolmanagement.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Course;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {

	public Optional<Course> findByName(String name);
	
}
