package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import com.capstone.schoolmanagement.model.Assignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.Register;

@Repository
public interface RegisterRepo extends PagingAndSortingRepository<Register, Long>, CrudRepository<Register, Long> {

  public Optional<List<Register>> getByKlassId(Long klassId);

  public boolean existsByKlassId(Long klassId);
}
