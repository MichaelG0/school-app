package com.capstone.schoolmanagement.responses;

import java.util.List;
import java.util.Optional;
import com.capstone.schoolmanagement.auth.users.UserBasicResponse;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.capstone.schoolmanagement.responses.users.TeacherBasicResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KlassResponse {
	private Long id;
	private Course course;
	private List<TeacherBasicResponse> teachers;
	private List<UserBasicResponse> students;
	private List<WeeklyScheduleItemResponse> weeklySchedule;

	public static KlassResponse buildKlassResponse(Klass klass) {
		return KlassResponse.builder()
				.id(klass.getId())
				.course(klass.getCourse())
				.teachers(klass.getTeachers()
						.stream()
						.map(TeacherBasicResponse::buildBasicTeacherResponse)
						.toList())
				.students(klass.getStudents()
						.stream()
						.map(UserBasicResponse::buildBasicUserResponse)
						.toList())
				.weeklySchedule(klass.getWeeklySchedule()
						.stream()
						.map(WeeklyScheduleItemResponse::buildWsiResponse)
						.toList())
				.build();
	}

}
