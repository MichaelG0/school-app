package com.capstone.schoolmanagement.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.capstone.schoolmanagement.model.users.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CompletedAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate submittedDate;
	@ManyToOne
	private Student student;
	private byte[] file;
	private int grade;
	@JsonBackReference
	@ManyToOne
	private Assignment assignment;
}
