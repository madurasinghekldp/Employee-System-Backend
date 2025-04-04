package org.ems.demo.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Department;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dep")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    //private String successStatus = "Success";

    @PostMapping
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createDep(@Valid @RequestBody Department department){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(departmentService.createDep(department))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllDep(@RequestParam(name="companyId") Long companyId){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(departmentService.getAll(companyId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-selected")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllDepsSelected(
            @RequestParam(name="companyId") Long companyId,
            @RequestParam(name="limit") String l,
            @RequestParam(name="offset") String o,
            @RequestParam(name="search") String s
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(departmentService.getAllSelected(companyId,l,o,s))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> updateDep(@Valid @RequestBody Department department){
        departmentService.updateDep(department);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @PermissionRequired(values = {"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> deleteDepById(@RequestParam(name="id") Long id){
        departmentService.deleteDep(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/count")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getDepartmentCount(@RequestParam(name="companyId") Long companyId){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(departmentService.getCount(companyId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
