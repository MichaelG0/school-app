package com.capstone.schoolmanagement.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.auth.users.UserResponse;
import com.capstone.schoolmanagement.dto.AssignmentResponse;
import com.capstone.schoolmanagement.model.Assignment;
import com.capstone.schoolmanagement.repos.AssignmentRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentService {
	private final AssignmentRepo assRepo;
	
	public AssignmentResponse getById(Long id) {
		Assignment assignment = assRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return AssignmentResponse.buildAssignmentResponse(assignment);
	}

	public Page<AssignmentResponse> getByKlassId(Long klassId, Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "dueDate");
		Page<Assignment> assPage = assRepo.findUpcomingByKlassId(klassId, pgb)
				.orElseThrow(() -> new EntityNotFoundException("Assignments not found by klass id" + klassId));

		List<AssignmentResponse> assList = assPage.stream()
				.map(ass -> AssignmentResponse.buildAssignmentResponse(ass))
				.toList();

		return new PageImpl<AssignmentResponse>(assList, pgb, assPage.getTotalElements());
	}

}
