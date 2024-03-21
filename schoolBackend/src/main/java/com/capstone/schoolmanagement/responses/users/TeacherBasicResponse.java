package com.capstone.schoolmanagement.responses.users;

import java.util.List;

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
	private List<String> modules;
	
	public static TeacherBasicResponse buildBasicTeacherResponse(TeacherModulesPerKlass teacherMPK) {
		Teacher teacher = teacherMPK.getTeacher();
		return TeacherBasicResponse.builder()
				.id(teacher.getId())
				.name(teacher.getName())
				.surname(teacher.getSurname())
				.avatar(teacher.getAvatar())
				.modules(teacherMPK.getModules().stream().map(mdl -> mdl.getName()).toList())
				.build();
	}
	
}
