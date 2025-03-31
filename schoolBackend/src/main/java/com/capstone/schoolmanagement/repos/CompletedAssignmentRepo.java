package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import com.capstone.schoolmanagement.model.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.CompletedAssignment;

@Repository
public interface CompletedAssignmentRepo extends PagingAndSortingRepository<CompletedAssignment, Long>, CrudRepository<CompletedAssignment, Long> {

  public Optional<List<CompletedAssignment>> findByStudentId(Long studentId);

  public Optional<CompletedAssignment> findByStudentIdAndAssignmentId(Long studentId, Long assignmentId);

  @Query("SELECT ca FROM CompletedAssignment ca WHERE ca.assignment.klass.id = :klassId AND ca.assignment.teacher.id = :teacherId")
  public Optional<Page<CompletedAssignment>> findByKlassAndTeacherIds(@Param("klassId") Long klassId, @Param("teacherId") Long teacherId, Pageable pgb);
}
