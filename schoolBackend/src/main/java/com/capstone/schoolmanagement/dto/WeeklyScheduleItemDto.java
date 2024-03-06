package com.capstone.schoolmanagement.dto;

import com.capstone.schoolmanagement.model.EWeekDay;
import lombok.Data;

@Data
public class WeeklyScheduleItemDto {
	private EWeekDay weekDay;
	private String startTime;
	private String endTime;
	private Long moduleId;
	private Long klassId;
}
