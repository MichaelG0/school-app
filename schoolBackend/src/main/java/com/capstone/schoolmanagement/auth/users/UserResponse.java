package com.capstone.schoolmanagement.auth.users;

import java.util.List;

import com.capstone.schoolmanagement.model.users.EGender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private String name;
	private String surname;
	private String email;
	private EGender gender;
	private String address;
	private List<String> roles;

	public static UserResponse buildUserResponse(AppUser user) {
		return UserResponse.builder()
				.name(user.getName())
				.surname(user.getSurname())
				.email(user.getEmail())
				.gender(user.getGender())
				.address(user.getAddress())
				.roles(user.getRoles().stream().map(role -> role.getRoleName().name()).toList())
				.build();
	}
	
}
