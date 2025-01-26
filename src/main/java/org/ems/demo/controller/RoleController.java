package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Role;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    //private String successStatus = "Success";

    @PostMapping
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createRole(@RequestBody Role role){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(roleService.createRole(role))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllRoles(){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(roleService.getAll())
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-selected")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllRolesSelected(
            @RequestParam(name="limit") String l,
            @RequestParam(name="offset") String o,
            @RequestParam(name="search") String s
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(roleService.getAllSelected(l,o,s))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> updateRole(@RequestBody Role role){
        roleService.updateRole(role);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @PermissionRequired(values = {"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> deleteRole(@RequestParam(name="id") Long id){
        roleService.deleteRole(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
