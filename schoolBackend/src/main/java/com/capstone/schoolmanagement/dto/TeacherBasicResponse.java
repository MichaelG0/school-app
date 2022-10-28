package com.capstone.schoolmanagement.dto;

import com.capstone.schoolmanagement.model.TeacherModulesPerKlass;
import com.capstone.schoolmanagement.model.users.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherBasicResponse {
	private Long id;
	private String name;
	private String surname;
	private String avatar;
	private String module;
	
	public static TeacherBasicResponse buildBasicTeacherResponse(TeacherModulesPerKlass teacherMPK) {
		Teacher teacher = teacherMPK.getTeacher();
		return TeacherBasicResponse.builder()
				.id(teacher.getId())
				.name(teacher.getName())
				.surname(teacher.getSurname())
				.avatar(teacher.getAvatar())
				.module(teacherMPK.getModule().getName())
				.build();
	}
	
}
