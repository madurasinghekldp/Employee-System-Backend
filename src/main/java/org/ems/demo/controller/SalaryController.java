package org.ems.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Salary;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createSalary(@Valid @RequestBody Salary salary){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(salaryService.createSalary(salary))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllSalary(
            @RequestParam(name="employeeId") Long employeeId,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="offset") int offset
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(salaryService.getAllSalary(employeeId,limit,offset))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updateSalary(
            @Valid @RequestBody Salary salary
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(salaryService.updateSalary(salary))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping()
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> deleteSalary(
            @RequestParam(name="id") Long id
    ){
        salaryService.deleteSalary(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
