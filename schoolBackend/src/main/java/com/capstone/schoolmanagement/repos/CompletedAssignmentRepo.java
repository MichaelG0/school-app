package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.CompletedAssignment;

@Repository
public interface CompletedAssignmentRepo extends CrudRepository<CompletedAssignment, Long> {
	
	public Optional<List<CompletedAssignment>> findByStudentId(Long studentId);
	
	public Optional<CompletedAssignment> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);

}
