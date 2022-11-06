package com.capstone.schoolmanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.auth.users.UserRepository;
import com.capstone.schoolmanagement.dto.AssignmentDto;
import com.capstone.schoolmanagement.dto.AssignmentResponse;
import com.capstone.schoolmanagement.model.Assignment;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.users.Teacher;
import com.capstone.schoolmanagement.repos.AssignmentRepo;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.capstone.schoolmanagement.repos.MmoduleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentService {
	private final AssignmentRepo assRepo;
	private final KlassRepo klsRepo;
	private final UserRepository usrRepo;
	private final MmoduleRepo mdlRepo;

	public AssignmentResponse create(AssignmentDto dto) {
		Klass klass = klsRepo.findById(dto.getKlassId())
				.orElseThrow(() -> new EntityNotFoundException("Klass not found"));
		Teacher tcr = (Teacher) usrRepo.findById(dto.getTeacherId())
				.orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
		Mmodule mdl = mdlRepo.findByName(dto.getModuleName())
				.orElseThrow(() -> new EntityNotFoundException("Module not found"));
		LocalDate dueDate = LocalDate.parse(dto.getDueDate());
		if (dueDate.isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Due date cannot be a past date");
		Assignment ass = new Assignment();
		ass.setIssueDate(LocalDate.now());
		ass.setDueDate(dueDate);
		ass.setTitle(dto.getTitle());
		ass.setCaption(dto.getCaption());
		ass.setKlass(klass);
		ass.setTeacher(tcr);
		ass.setModule(mdl);
		assRepo.save(ass);
		return AssignmentResponse.buildAssignmentResponse(ass);
	}

	public AssignmentResponse getById(Long id) {
		Assignment assignment = assRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return AssignmentResponse.buildAssignmentResponse(assignment);
	}

	public Page<AssignmentResponse> getUpcomingByKlassId(Long klassId, Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "dueDate");
		Page<Assignment> assPage = assRepo.findUpcomingByKlassId(klassId, pgb)
				.orElseThrow(() -> new EntityNotFoundException("Assignments not found by klass id" + klassId));
		List<AssignmentResponse> assList = assPage.stream()
				.map(ass -> AssignmentResponse.buildAssignmentResponse(ass))
				.toList();
		return new PageImpl<AssignmentResponse>(assList, pgb, assPage.getTotalElements());
	}
	
	public Page<AssignmentResponse> getPastByKlassId(Long klassId, Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, "dueDate");
		Page<Assignment> assPage = assRepo.findPastByKlassId(klassId, pgb)
				.orElseThrow(() -> new EntityNotFoundException("Assignments not found by klass id" + klassId));
		List<AssignmentResponse> assList = assPage.stream()
				.map(ass -> AssignmentResponse.buildAssignmentResponse(ass))
				.toList();
		return new PageImpl<AssignmentResponse>(assList, pgb, assPage.getTotalElements());
	}

	public Page<AssignmentResponse> getUpcomingByKlassAndTeacherIds(Long klassId, Long teacherId, Optional<Integer> page,
			Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "dueDate");
		Page<Assignment> assPage = assRepo.findUpcomingByKlassAndTeacherIds(klassId, teacherId, pgb)
				.orElseThrow(() -> new EntityNotFoundException("Assignments not found"));
		List<AssignmentResponse> assList = assPage.stream()
				.map(ass -> AssignmentResponse.buildAssignmentResponse(ass))
				.toList();
		return new PageImpl<AssignmentResponse>(assList, pgb, assPage.getTotalElements());
	}
	
	public Page<AssignmentResponse> getPastByKlassAndTeacherIds(Long klassId, Long teacherId, Optional<Integer> page,
			Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, "dueDate");
		Page<Assignment> assPage = assRepo.findPastByKlassAndTeacherIds(klassId, teacherId, pgb)
				.orElseThrow(() -> new EntityNotFoundException("Assignments not found"));
		List<AssignmentResponse> assList = assPage.stream()
				.map(ass -> AssignmentResponse.buildAssignmentResponse(ass))
				.toList();
		return new PageImpl<AssignmentResponse>(assList, pgb, assPage.getTotalElements());
	}

	public AssignmentResponse update(Long id, AssignmentDto dto) {
		Assignment ass = assRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
		Mmodule mdl = mdlRepo.findByName(dto.getModuleName())
				.orElseThrow(() -> new EntityNotFoundException("Module not found"));
		LocalDate dueDate = LocalDate.parse(dto.getDueDate());
		if (dueDate.isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Due date cannot be a past date");
		ass.setDueDate(dueDate);
		ass.setTitle(dto.getTitle());
		ass.setCaption(dto.getCaption());
		ass.setModule(mdl);
		assRepo.save(ass);
		return AssignmentResponse.buildAssignmentResponse(ass);
	}

	public void delete(Long id) {
		if (!assRepo.existsById(id))
			throw new EntityNotFoundException("User not found");
		assRepo.deleteById(id);
	}

}
