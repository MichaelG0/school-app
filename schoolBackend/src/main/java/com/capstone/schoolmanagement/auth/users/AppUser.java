package com.capstone.schoolmanagement.auth.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.capstone.schoolmanagement.auth.roles.AppRole;
import com.capstone.schoolmanagement.model.users.EGender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
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
	
//	ACCESS DATA
	@NotBlank
	@Size(max = 100)
	private String email;
	@NotBlank
	@Size(min = 8, max = 120)
	@JsonIgnore
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<AppRole> roles = new HashSet<AppRole>();

//	public AppUser(Long id, @NotBlank @Size(max = 100) String email, @NotBlank @Size(min = 3, max = 120) String password) {
//		super();
//		this.id = id;
//		this.email = email;
//		this.password = password;
//	}

}
