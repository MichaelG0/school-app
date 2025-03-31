package com.capstone.schoolmanagement.auth.users;

import java.util.HashSet;
import java.util.Set;

import com.capstone.schoolmanagement.auth.roles.AppRole;
import com.capstone.schoolmanagement.model.users.EGender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  private Long id;
  @NotBlank
  private String name;
  @NotBlank
  private String surname;
  @Enumerated(EnumType.STRING)
  private EGender gender;
  private String address;
  private String avatar;
  private String phone;
  @Column(columnDefinition = "TEXT")
  private String bio;

  //	ACCESS DATA
  @NotBlank
  @Size(max = 100)
  private String email;
  @NotBlank
  @JsonIgnore
  @Size(min = 8, max = 120)
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<AppRole> roles = new HashSet<AppRole>();
}
