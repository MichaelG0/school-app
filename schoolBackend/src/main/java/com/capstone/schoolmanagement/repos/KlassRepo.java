package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;

@Repository
public interface KlassRepo extends PagingAndSortingRepository<Klass, Long>, CrudRepository<Klass, Long> {

  public Optional<List<Klass>> findByCourse(Course course);

  public Optional<Klass> findByStudentsId(Long id);

  public Optional<List<Klass>> findByTeachersTeacherId(Long id);
}
