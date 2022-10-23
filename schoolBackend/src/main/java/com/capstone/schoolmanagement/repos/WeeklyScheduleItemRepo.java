package com.capstone.schoolmanagement.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.schoolmanagement.model.WeeklyScheduleItem;

@Repository
public interface WeeklyScheduleItemRepo extends CrudRepository<WeeklyScheduleItem, Long> {

}
