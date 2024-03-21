package com.capstone.schoolmanagement.responses;

import java.time.LocalTime;
import com.capstone.schoolmanagement.model.EWeekDay;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.KlassBasicResponse;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeeklyScheduleItemResponse {
	private Long id;
	private EWeekDay weekDay;
	private LocalTime startTime;
	private LocalTime endTime;
	private Mmodule module;
	private KlassBasicResponse klass;

	public static WeeklyScheduleItemResponse buildWsiResponse(WeeklyScheduleItem wsi) {
		return WeeklyScheduleItemResponse.builder()
				.id(wsi.getId())
				.weekDay(wsi.getWeekDay())
				.startTime(wsi.getStartTime())
				.endTime(wsi.getEndTime())
				.module(wsi.getModule())
				.klass(KlassBasicResponse.buildKlassBasicResponse(wsi.getKlass()))
				.build();
	}
}
