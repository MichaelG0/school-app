package com.capstone.schoolmanagement.model.users;

import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Course;
import com.capstone.schoolmanagement.model.Klass;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends AppUser {
  @ManyToOne
  private Course course;
  @JsonBackReference
  @ManyToOne
  private Klass klass;
}
