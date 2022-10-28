package com.capstone.schoolmanagement.model.users;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.model.Mmodule;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Teacher extends AppUser {
	@ManyToMany
	private Set<Mmodule> modules;
}
