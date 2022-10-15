package com.capstone.schoolmanagement.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.users.StudentConfirmationToken;

@Repository
public interface StudentConfirmationTokenRepo extends CrudRepository<StudentConfirmationToken, UUID> {
	
	public Optional<StudentConfirmationToken> getByUserId(Long id);
	
	public Optional<StudentConfirmationToken> getByToken(String token);
	
}
