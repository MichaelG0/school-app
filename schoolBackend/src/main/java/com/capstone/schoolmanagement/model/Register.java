package com.capstone.schoolmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.capstone.schoolmanagement.model.users.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
