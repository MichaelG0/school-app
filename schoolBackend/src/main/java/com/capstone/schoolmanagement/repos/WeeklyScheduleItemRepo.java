package com.capstone.schoolmanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.CourseInfo;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;

@Repository
public interface WeeklyScheduleItemRepo extends CrudRepository<WeeklyScheduleItem, Long> {
	public Optional<List<WeeklyScheduleItem>> findByKlassId(Long id);
}
