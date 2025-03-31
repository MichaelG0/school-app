package com.capstone.schoolmanagement.model.users;

import java.util.HashSet;
import java.util.Set;

import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.Register;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Teacher extends AppUser {
  @ManyToMany
  private Set<Mmodule> modules;
  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
  private Set<WeeklyScheduleItem> weeklySchedule = new HashSet<WeeklyScheduleItem>();
}
