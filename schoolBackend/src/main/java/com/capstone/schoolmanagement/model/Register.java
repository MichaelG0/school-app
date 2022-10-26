package com.capstone.schoolmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.capstone.schoolmanagement.model.users.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Register {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	@ManyToOne
	private Mmodule module;
	@JsonBackReference
	@ManyToOne
	private Klass klass;
	@ManyToMany
	private Set<Student> absent = new HashSet<Student>();
}
