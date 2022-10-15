package com.capstone.schoolmanagement.auth.jwt;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private final String type = "Bearer";
	private Long id;
	private String email;
	private String name;
	private String surname;
	private List<String> roles;
	
	public JwtResponse(String token, Long id, String email, String name, String surname, List<String> roles) {
		this.token = token;
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.roles = roles;
	}

}
