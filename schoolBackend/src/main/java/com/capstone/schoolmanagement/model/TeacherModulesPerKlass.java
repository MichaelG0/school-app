package com.capstone.schoolmanagement.model;

import java.util.HashSet;
import java.util.Set;

import com.capstone.schoolmanagement.model.users.Teacher;
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
