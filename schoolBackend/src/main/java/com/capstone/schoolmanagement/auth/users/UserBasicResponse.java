package com.capstone.schoolmanagement.auth.users;

import com.capstone.schoolmanagement.model.users.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBasicResponse {
	private Long id;
	private String name;
	private String surname;
	private String avatar;
	private String module;

	public static UserBasicResponse buildBasicUserResponse(AppUser user) {
		return UserBasicResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.surname(user.getSurname())
				.avatar(user.getAvatar())
				.build();
	}
	
	public static UserBasicResponse buildBasicTeacherResponse(Teacher user) {
		return UserBasicResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.surname(user.getSurname())
				.avatar(user.getAvatar())
				.build();
	}
}
