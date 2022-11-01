package com.capstone.schoolmanagement.dto;

import java.util.List;

import com.capstone.schoolmanagement.model.TeacherModulesPerKlass;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherModulesPerKlassResponse {
	private Long id;
	private Long klassId;
	private String klassCourse;
	private List<String> modules;
	
	public static TeacherModulesPerKlassResponse buildResponse(TeacherModulesPerKlass teacherMPK) {
		return TeacherModulesPerKlassResponse.builder()
				.id(teacherMPK.getId())
				.klassId(teacherMPK.getKlass().getId())
				.klassCourse(teacherMPK.getKlass().getCourse().getInfo().getName())
				.modules(teacherMPK.getModules().stream().map(mdl -> mdl.getName()).toList())
				.build();
	}
}
