package com.capstone.schoolmanagement.model.users;

import java.time.LocalDate;

import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class StudentConfirmationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  private LocalDate expirationDate;
  @JsonIgnore
  @OneToOne
  private AppUser user;
  @OneToOne
  private Course course;
}
