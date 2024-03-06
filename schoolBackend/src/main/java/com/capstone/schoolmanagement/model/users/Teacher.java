package com.capstone.schoolmanagement.model.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Mmodule;
import com.capstone.schoolmanagement.model.Register;
import com.capstone.schoolmanagement.model.WeeklyScheduleItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
