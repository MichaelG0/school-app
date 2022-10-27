package com.capstone.schoolmanagement.dto;

import java.util.List;

import com.capstone.schoolmanagement.auth.users.UserBasicResponse;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KlassResponse {
	private Long id;
	private Course course;
	private List<UserBasicResponse> teachers;
	private List<UserBasicResponse> students;
	private List<WeeklyScheduleItem> weeklySchedule;

	public static KlassResponse buildKlassResponse(Klass klass) {
		return KlassResponse.builder()
				.id(klass.getId())
				.course(klass.getCourse())
				.students(klass.getStudents()
						.stream()
						.map(student -> UserBasicResponse.buildBasicUserResponse(student))
						.toList())
				.weeklySchedule(klass.getWeeklySchedule().stream().toList())
				.build();
	}
}
