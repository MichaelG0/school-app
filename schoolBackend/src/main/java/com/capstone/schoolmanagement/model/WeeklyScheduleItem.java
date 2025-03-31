package com.capstone.schoolmanagement.model;

import java.time.LocalTime;

import com.capstone.schoolmanagement.model.users.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WeeklyScheduleItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  private EWeekDay weekDay;
  private LocalTime startTime;
  private LocalTime endTime;
  @ManyToOne
  private Mmodule module;
  @JsonBackReference
  @ManyToOne
  private Teacher teacher;
  @JsonBackReference
  @ManyToOne
  private Klass klass;

  public boolean setTeacher(Teacher tcr) {
    boolean setSuccessfully = true;

    if (this.weekDay == null || this.startTime == null || this.endTime == null)
      throw new IllegalArgumentException("weekDay, startTime and endTime must be set");

    for (WeeklyScheduleItem tcrWsi : tcr.getWeeklySchedule()) {
      if (this.weekDay.equals(tcrWsi.getWeekDay())) {
        if (this.startTime.isBefore(tcrWsi.getEndTime()) && this.endTime.isAfter(tcrWsi.getStartTime())) {
          setSuccessfully = false;
          return setSuccessfully;
        }
      }
    }

    this.teacher = tcr;
    return setSuccessfully;
  }

  public void setStartTime(LocalTime startTime) {
    if (this.endTime != null && startTime.compareTo(this.endTime) >= 0)
      throw new IllegalArgumentException("Start time must be before end time");
    this.startTime = startTime;
  }

  public void setEndTime(LocalTime endTime) {
    if (this.startTime != null && endTime.compareTo(this.startTime) <= 0)
      throw new IllegalArgumentException("End time must be after start time");
    this.endTime = endTime;
  }
}
