package com.capstone.schoolmanagement.auth.users;

import java.util.List;

import com.capstone.schoolmanagement.model.users.EGender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String avatar;
	private EGender gender;
	private String address;
	private List<String> roles;

	public static UserResponse buildUserResponse(AppUser user) {
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.surname(user.getSurname())
				.email(user.getEmail())
				.avatar(user.getAvatar())
				.gender(user.getGender())
				.address(user.getAddress())
				.roles(user.getRoles().stream().map(role -> role.getRoleName().name()).toList())
				.build();
	}

	public static UserResponse buildUserResponse(UserDetailsImpl user) {
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.surname(user.getSurname())
				.email(user.getUsername())
				.avatar(user.getAvatar())
				.gender(user.getGender())
				.address(user.getAddress())
				.roles(user.getAuthorities().stream().map(role -> role.getAuthority()).toList())
				.build();
	}

}
