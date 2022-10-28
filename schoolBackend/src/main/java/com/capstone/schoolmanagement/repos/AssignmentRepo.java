package com.capstone.schoolmanagement.repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Assignment;

@Repository
public interface AssignmentRepo extends PagingAndSortingRepository<Assignment, Long> {

	public Optional<Page<Assignment>> findByKlassId(Long klassId, Pageable pgb);
	
}
