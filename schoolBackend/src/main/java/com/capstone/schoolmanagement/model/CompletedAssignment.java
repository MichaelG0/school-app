package com.capstone.schoolmanagement.model;

import java.time.LocalDate;

import com.capstone.schoolmanagement.model.users.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
  private String link;
  private float grade;
  @JsonBackReference
  @ManyToOne
  private Assignment assignment;

  public void setGrade(float grade) {
    if (grade < 1 || grade > 10)
      throw new IllegalArgumentException("Grade must be a float number between 1 and 10");
    this.grade = (float) (Math.floor(grade * 10.0) / 10.0);
  }
}
