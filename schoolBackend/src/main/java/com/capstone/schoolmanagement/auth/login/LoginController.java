package com.capstone.schoolmanagement.auth.login;

import java.util.List;
import java.util.stream.Collectors;

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
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
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
		List<String> roles = userDetails.getAuthorities()
				.stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		System.out.println(userDetails.toString());

		JwtResponse jwtresp = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getName(), userDetails.getSurname(), roles);

		return ResponseEntity.ok(jwtresp);
	}

}
