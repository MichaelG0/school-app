package com.capstone.schoolmanagement.model.users;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.model.Mmodule;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Teacher extends AppUser {
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "teachers")
	private Set<Klass> klasses;
	@ManyToMany
	private Set<Mmodule> modules;
}
