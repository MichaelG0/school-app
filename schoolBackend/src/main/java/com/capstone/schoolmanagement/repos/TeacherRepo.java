package com.capstone.schoolmanagement.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.users.Teacher;

@Repository
public interface TeacherRepo extends CrudRepository<Teacher, Long>{

}
