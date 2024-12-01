package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Role;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

    private final RoleService roleService;
    private String successStatus = "Success";

    @PostMapping
    public ResponseEntity<SuccessResponse> createRole(@RequestBody Role role){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(roleService.createRole(role))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getAllRoles(){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(roleService.getAll())
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-selected")
    public ResponseEntity<SuccessResponse> getAllRolesSelected(
            @RequestParam(name="limit") String l,
            @RequestParam(name="offset") String o
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(roleService.getAllSelected(l,o))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> updateRole(@RequestBody Role role){
        roleService.updateRole(role);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> deleteRole(@RequestParam(name="id") Long id){
        roleService.deleteRole(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
