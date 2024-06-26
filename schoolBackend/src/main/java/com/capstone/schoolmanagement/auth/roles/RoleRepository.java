package com.capstone.schoolmanagement.auth.roles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

	public Optional<AppRole> findByRoleName(ERole roleName);

	public List<AppRole> findAllByRoleNameIn(List<ERole> roleNames);

}
