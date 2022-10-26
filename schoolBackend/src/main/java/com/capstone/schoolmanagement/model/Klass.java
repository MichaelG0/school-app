package com.capstone.schoolmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.capstone.schoolmanagement.model.users.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Klass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Course course;
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass")
    private Set<Student> students = new HashSet<Student>();
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass")
	private Set<WeeklyScheduleItem> weeklySchedule = new HashSet<WeeklyScheduleItem>();
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "klass")
	private Set<Register> registers = new HashSet<Register>();
}
