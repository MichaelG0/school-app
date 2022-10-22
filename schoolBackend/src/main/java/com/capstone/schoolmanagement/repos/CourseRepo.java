package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.model.ECourse;

@Repository
public interface CourseRepo extends CrudRepository<CourseInfo, Long> {

	public Optional<CourseInfo> findByName(String name);
	
	public Optional<List<CourseInfo>> findByType(ECourse type);
	
}
