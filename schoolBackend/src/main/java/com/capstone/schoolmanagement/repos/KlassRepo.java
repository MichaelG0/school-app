package com.capstone.schoolmanagement.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Klass;

@Repository
public interface KlassRepo extends PagingAndSortingRepository<Klass, Long> {
	
	public List<Klass> findByTeacherName(String name);
	
}
