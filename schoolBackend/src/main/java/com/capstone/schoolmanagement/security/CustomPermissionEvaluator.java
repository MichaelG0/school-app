package com.capstone.schoolmanagement.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.capstone.schoolmanagement.auth.roles.AppRole;
import com.capstone.schoolmanagement.auth.roles.ERole;
import com.capstone.schoolmanagement.auth.users.AppUser;
import com.capstone.schoolmanagement.auth.users.UserRepository;
import com.capstone.schoolmanagement.dto.users.UserRolesDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
	private final UserRepository usrRepo;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (targetDomainObject instanceof List) {
			@SuppressWarnings("unchecked")
			List<UserRolesDto> usersRoles = (List<UserRolesDto>) targetDomainObject;
			for (UserRolesDto userRoles : usersRoles) {
				if (!hasRolePermission(authentication, userRoles.getUserId(), userRoles.getRoles())) {
					return false; // User doesn't have permission
				}
			}
			return true; // All users have permission
		}
		return false; // Invalid targetDomainObject
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean hasRolePermission(Authentication authentication, Long userId, List<String> roles) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean isAdmin = authorities.stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		boolean isStaff = authorities.stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STAFF"));

		// Check if the user has ROLE_ADMIN or ROLE_STAFF
		if (isAdmin || isStaff) {
			// Check if the user is trying to add/remove ADMIN role
			if (roles.contains("ROLE_ADMIN")) {
				// If user is not admin and trying to add ADMIN role, deny permission
				if (!isAdmin) {
					return false;
				}
			}
			// Check if the user is trying to remove ADMIN role
			AppUser user = usrRepo.findById(userId).orElseThrow(); // Implement your userService to fetch user by ID
			System.out.println(user);
			if (user != null
					&& user.getRoles().stream().map(AppRole::getRoleName).toList().contains(ERole.ROLE_ADMIN)) {
				// If user is not admin and trying to remove ADMIN role, deny permission
				if (!isAdmin) {
					return false;
				}
			}
			return true; // User has permission
		}
		return false; // User doesn't have necessary role
	}
}
