package com.capstone.schoolmanagement.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.RegisterDto;
import com.capstone.schoolmanagement.dto.RegisterResponse;
import com.capstone.schoolmanagement.model.Register;
import com.capstone.schoolmanagement.model.users.Student;
import com.capstone.schoolmanagement.repos.RegisterRepo;
import com.capstone.schoolmanagement.repos.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {
	private final RegisterRepo rgsRepo;
	private final StudentRepo stdRepo;
	private final KlassService klsSrv;

	public RegisterResponse create(RegisterDto dto) {
		Register rgs = new Register();		
		rgs.setDate(LocalDate.parse(dto.getDate()));
		rgs.setKlass(klsSrv.getById(dto.getKlassId()));
		rgs.setAbsent(Set.copyOf(dto.getAbsent()));
		rgsRepo.save(rgs);
		return RegisterResponse.buildRegisterResponse(rgs);
	}
	
	public int getStudentAttendancePercentage(Long studentId, Long klassId) {
		Student student = stdRepo.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
		List<Register> registers = new ArrayList<Register>();
		if (rgsRepo.existsByKlassId(klassId))
			registers = rgsRepo.getByKlassId(klassId).get();
		else return 100;
		List<Register> absences = registers.stream().filter(reg -> reg.getAbsent().contains(student)).toList();
		return 100 - (absences.size() * 100 / registers.size());
	}
	
}
