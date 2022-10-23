package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;

@Repository
public interface KlassRepo extends PagingAndSortingRepository<Klass, Long> {
	
	public Optional<List<Klass>> findByCourse(Course course);
	
	public Optional<Klass> findByStudentsId(Long id);
	
}
