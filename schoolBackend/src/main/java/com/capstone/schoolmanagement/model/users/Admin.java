package com.capstone.schoolmanagement.model.users;

import javax.persistence.Entity;

import com.capstone.schoolmanagement.auth.users.AppUser;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin extends AppUser {

}
