package com.capstone.schoolmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.capstone.schoolmanagement.model.users.Teacher;

import lombok.Data;

@Entity
@Table(name = "classes")
@Data
public class Klass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Teacher teacher;
}
