package com.capstone.schoolmanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.capstone.schoolmanagement.model.users.Teacher;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
