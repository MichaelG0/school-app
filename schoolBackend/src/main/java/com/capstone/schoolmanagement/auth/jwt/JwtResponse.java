package com.capstone.schoolmanagement.auth.jwt;

import com.capstone.schoolmanagement.auth.users.UserResponse;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private final String type = "Bearer";
	private UserResponse user;
	
	public JwtResponse(String token, UserResponse user) {
		this.token = token;
		this.user = user;
	}

}
