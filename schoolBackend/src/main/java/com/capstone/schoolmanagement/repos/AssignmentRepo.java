package com.capstone.schoolmanagement.repos;

import java.util.Optional;

import com.capstone.schoolmanagement.model.Klass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Assignment;

@Repository
public interface AssignmentRepo extends PagingAndSortingRepository<Assignment, Long>, CrudRepository<Assignment, Long> {

  @Query("SELECT a FROM Assignment a WHERE a.klass.id = :klassId AND a.dueDate >= CURRENT_DATE")
  public Optional<Page<Assignment>> findUpcomingByKlassId(@Param("klassId") Long klassId, Pageable pgb);

  @Query("SELECT a FROM Assignment a WHERE a.klass.id = :klassId AND a.dueDate < CURRENT_DATE")
  public Optional<Page<Assignment>> findPastByKlassId(@Param("klassId") Long klassId, Pageable pgb);

  @Query("SELECT a FROM Assignment a WHERE a.klass.id = :klassId AND a.teacher.id = :teacherId AND a.dueDate >= CURRENT_DATE")
  public Optional<Page<Assignment>> findUpcomingByKlassAndTeacherIds(@Param("klassId") Long klassId, @Param("teacherId") Long teacherId, Pageable pgb);

  @Query("SELECT a FROM Assignment a WHERE a.klass.id = :klassId AND a.teacher.id = :teacherId AND a.dueDate < CURRENT_DATE")
  public Optional<Page<Assignment>> findPastByKlassAndTeacherIds(@Param("klassId") Long klassId, @Param("teacherId") Long teacherId, Pageable pgb);
}
