package com.capstone.schoolmanagement.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Course;

@Repository
public interface CourseRepo extends CrudRepository<Course, Long> {

}
