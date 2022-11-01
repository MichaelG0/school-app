package com.capstone.schoolmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.capstone.schoolmanagement.model.users.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TeacherModulesPerKlass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonBackReference
	@ManyToOne
	private Klass klass;
	@ManyToOne
	private Teacher teacher;
	@ManyToMany
	private Set<Mmodule> modules = new HashSet<Mmodule>();
}
