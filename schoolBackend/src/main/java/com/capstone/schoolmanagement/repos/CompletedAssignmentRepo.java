package com.capstone.schoolmanagement.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.CompletedAssignment;

@Repository
public interface CompletedAssignmentRepo extends CrudRepository<CompletedAssignment, Long> {

}
