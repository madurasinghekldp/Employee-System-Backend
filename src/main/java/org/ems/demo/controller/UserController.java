package org.ems.demo.controller;


import jakarta.validation.Valid;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.dto.UpdatePassword;
import org.ems.demo.dto.UpdateUser;
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
    public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody RegisterUserDto user){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(userService.createUser(user))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PatchMapping("/profile")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updateUser(
            @RequestParam(name="id") Integer id,
            @Valid @RequestBody UpdateUser user
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(userService.updateUser(id,user))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PatchMapping("/password")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updatePassword(
            @RequestParam(name="id") Integer id,
            @Valid @RequestBody UpdatePassword updatePassword
            ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(userService.updatePassword(id,updatePassword))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @PermissionRequired(values={"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> deleteUser(
            @RequestParam(name="id") Integer id
    ){
        userService.deleteUser(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(null)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
