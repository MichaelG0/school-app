package com.capstone.schoolmanagement.dto.users;

import java.util.List;

import lombok.Data;

@Data
public class UserRolesDto {
	Long userId;
	List<String> roles; //ERole
}
