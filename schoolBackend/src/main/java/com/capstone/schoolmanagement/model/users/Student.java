package com.capstone.schoolmanagement.model.users;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends AppUser {
	@ManyToOne
	private Course course;
	@ManyToOne
	private Klass klass;
}
