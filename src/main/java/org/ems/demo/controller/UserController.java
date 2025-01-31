package org.ems.demo.controller;


import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/by-email")
//    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
//    public ResponseEntity<UserEntity> authenticatedUser(@RequestParam(name = "email") String email) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        UserEntity currentUserEntity = (UserEntity) authentication.getPrincipal();
//
//        return ResponseEntity.ok(currentUserEntity);
//    }

    @GetMapping()
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<List<UserEntity>> allUsers() {
        List <UserEntity> userEntities = userService.allUsers();

        return ResponseEntity.ok(userEntities);
    }

    @GetMapping("/by-email")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> authenticatedUser(@RequestParam(name = "email") String email) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(userService.getUserByEmail(email))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PostMapping()
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createUser(@RequestBody RegisterUserDto user){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(userService.createUser(user))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
