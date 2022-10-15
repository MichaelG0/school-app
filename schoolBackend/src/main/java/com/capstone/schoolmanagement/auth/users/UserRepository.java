package com.capstone.schoolmanagement.auth.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

	public Optional<AppUser> findByEmail(String name);

	public Boolean existsByEmail(String name);

}
