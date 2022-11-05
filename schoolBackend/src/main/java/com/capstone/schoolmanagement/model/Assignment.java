package com.capstone.schoolmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.capstone.schoolmanagement.model.users.Teacher;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate issueDate;
	private LocalDate dueDate;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String caption;
	@ManyToOne
	private Klass klass;
	@ManyToOne
	private Teacher teacher;
	@ManyToOne
	private Mmodule module;
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assignment", cascade = CascadeType.ALL)
	private Set<CompletedAssignment> completedAssignments = new HashSet<CompletedAssignment>();	
}
