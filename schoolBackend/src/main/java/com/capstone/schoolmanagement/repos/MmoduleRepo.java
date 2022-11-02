package com.capstone.schoolmanagement.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Mmodule;

@Repository
public interface MmoduleRepo extends CrudRepository<Mmodule, Long> {

	public Optional<Mmodule> findByName(String name);
	
}
