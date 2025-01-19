package org.ems.demo.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RoleCheckAspect {

    @Before("@annotation(permissionRequired)")
    public void checkRole(PermissionRequired permissionRequired){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }

        var userRoles = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority()) // Extract roles as strings
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // Normalize role format
                .toList();

        boolean hasRequiredRole = Arrays.stream(permissionRequired.values())
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // Normalize annotation roles
                .anyMatch(userRoles::contains);

        if (!hasRequiredRole) {
            throw new AccessDeniedException("User does not have the required role(s)");
        }
    }
}
