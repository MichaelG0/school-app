package com.capstone.schoolmanagement.auth.login;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.schoolmanagement.auth.jwt.JwtResponse;
import com.capstone.schoolmanagement.auth.jwt.JwtUtils;
import com.capstone.schoolmanagement.auth.users.UserDetailsImpl;
import com.capstone.schoolmanagement.auth.users.UserResponse;

import lombok.RequiredArgsConstructor;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	private final AuthenticationManager authManager;
	private final JwtUtils jwtUtils;
	
	@PostMapping
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		Authentication authentication = authManager.authenticate(usrNameAuth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		System.out.println(userDetails.toString());

		JwtResponse jwtresp = new JwtResponse(jwt, UserResponse.buildUserResponse(userDetails));

		return ResponseEntity.ok(jwtresp);
	}

}
