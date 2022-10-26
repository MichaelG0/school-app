package com.capstone.schoolmanagement.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.users.Student;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {
	
}
