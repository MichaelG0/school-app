package com.capstone.schoolmanagement.auth.users;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capstone.schoolmanagement.auth.jwt.JwtResponse;
import com.capstone.schoolmanagement.auth.jwt.JwtUtils;
import com.capstone.schoolmanagement.auth.login.LoginController;
import com.capstone.schoolmanagement.auth.login.LoginRequest;
import com.capstone.schoolmanagement.controllers.IControllerPage;
import com.capstone.schoolmanagement.dto.StudentConfirmationToken;
import com.capstone.schoolmanagement.dto.StudentDto;
import com.capstone.schoolmanagement.model.users.Student;

import lombok.RequiredArgsConstructor;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements IControllerPage<UserResponse, UserDto> {
	private final UserService usrSrv;
	private final AuthenticationManager authManager;
	private final JwtUtils jwtUtils;

	@Override
	@PostMapping
	public ResponseEntity<JwtResponse> create(@RequestBody UserDto usrDto) {
		LoginRequest request = usrSrv.create(usrDto);
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		Authentication authentication = authManager.authenticate(usrNameAuth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		JwtResponse jwtresp = new JwtResponse(jwt, UserResponse.buildUserResponse(userDetails));

		return ResponseEntity.ok(jwtresp);
	}

	@PostMapping("/apply")
	public ResponseEntity<UserResponse> studentApplication(@RequestBody @Valid StudentDto studentDto) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(usrSrv.studentApplication(studentDto));
	}

	@GetMapping("/enrol")
	public ResponseEntity<UserResponse> enrolStudent(@RequestParam String token) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		return ResponseEntity.created(uri).body(usrSrv.enrolStudent(token));
	}

	@Override
	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAll(@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size) {
		return ResponseEntity.ok(usrSrv.getAllUsersBasicInformations(page, size));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(usrSrv.getUserBasicInformations(id));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserDto usrDto) {
		return ResponseEntity.ok(usrSrv.update(id, usrDto));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		usrSrv.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{userId}/add_role")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> addRole(@PathVariable Long userId, @RequestBody String roleName) {
		usrSrv.addRole(userId, roleName);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{userId}/remove_role")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> removeRole(@PathVariable Long userId, @RequestBody String roleName) {
		usrSrv.removeRole(userId, roleName);
		return ResponseEntity.ok().build();
	}

}
