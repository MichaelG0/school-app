package com.capstone.schoolmanagement.responses;

import java.time.LocalDate;
import java.util.List;

import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Register;
import com.capstone.schoolmanagement.model.users.Student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
	private Long id;
	private LocalDate date;
	private Klass klass;
	private List<Student> absent;
	
	public static RegisterResponse buildRegisterResponse(Register register) {
		return RegisterResponse.builder()
				.id(register.getId())
				.date(register.getDate())
				.absent(register.getAbsent().stream().toList())
				.build();
	}
}
