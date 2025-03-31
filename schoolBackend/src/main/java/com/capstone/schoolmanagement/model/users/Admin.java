package com.capstone.schoolmanagement.model.users;

import com.capstone.schoolmanagement.auth.users.AppUser;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin extends AppUser {

}
