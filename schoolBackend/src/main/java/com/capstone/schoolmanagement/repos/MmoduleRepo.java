package com.capstone.schoolmanagement.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Mmodule;

@Repository
public interface MmoduleRepo extends CrudRepository<Mmodule, Long> {

}
