package com.capstone.schoolmanagement.auth.users;

import lombok.Data;

@Data
public class UserDto {
	private String name;
	private String surname;
	private String email;
	private String password;
	private String gender;
	private String address;
}
