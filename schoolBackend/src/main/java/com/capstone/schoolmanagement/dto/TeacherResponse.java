package com.capstone.schoolmanagement.dto;

import java.util.List;
import java.util.Set;

import com.capstone.schoolmanagement.auth.roles.AppRole;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.capstone.schoolmanagement.model.users.EGender;
import com.capstone.schoolmanagement.model.users.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponse {
	private Long id;
	private String name;
	private String surname;
	private EGender gender;
	private String address;
	private String avatar;
	private String phone;
	private String bio;
	private String email;
	private Set<Mmodule> modules;
	private List<WeeklyScheduleItemResponse> weeklySchedule;

	public static TeacherResponse buildTeacherResponse(Teacher teacher) {
		return TeacherResponse.builder()
				.id(teacher.getId())
				.name(teacher.getName())
				.surname(teacher.getSurname())
				.gender(teacher.getGender())
				.address(teacher.getAddress())
				.avatar(teacher.getAvatar())
				.phone(teacher.getPhone())
				.bio(teacher.getBio())
				.email(teacher.getEmail())
				.modules(teacher.getModules())
				.weeklySchedule(teacher.getWeeklySchedule()
						.stream()
						.map(wsi -> WeeklyScheduleItemResponse.buildWsiResponse(wsi))
						.toList())
				.build();
	}
}
