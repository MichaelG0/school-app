package com.capstone.schoolmanagement.services;

import java.time.LocalTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.WeeklyScheduleItemDto;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.capstone.schoolmanagement.repos.MmoduleRepo;
import com.capstone.schoolmanagement.repos.WeeklyScheduleItemRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeeklyScheduleItemService {
	private final WeeklyScheduleItemRepo wsiRepo;
	private final KlassRepo klsRepo;
	private final MmoduleRepo mdlRepo;

	public WeeklyScheduleItem create(WeeklyScheduleItemDto wsiDto) {
		Klass klass = klsRepo.findById(wsiDto.getKlassId())
				.orElseThrow(() -> new EntityNotFoundException("Klass not found"));
		Mmodule mdl = mdlRepo.findById(wsiDto.getModuleId())
				.orElseThrow(() -> new EntityNotFoundException("Module not found"));
		LocalTime startTime = LocalTime.parse(wsiDto.getStartTime());
		LocalTime endTime = LocalTime.parse(wsiDto.getEndTime());
		if (endTime.compareTo(startTime) <= 0)
			throw new IllegalArgumentException("End time must be after start time");

		WeeklyScheduleItem wsi = new WeeklyScheduleItem();
		wsi.setKlass(klass);
		wsi.setModule(mdl);
		wsi.setStartTime(startTime);
		wsi.setEndTime(endTime);
		wsi.setWeekDay(wsiDto.getWeekDay());
		return wsiRepo.save(wsi);
	}
}
