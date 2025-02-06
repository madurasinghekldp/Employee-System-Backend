package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Employee;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    //private final String successStatus = "Success";

    @PostMapping
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createEmp(@RequestBody Employee employee){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(employeeService.create(employee))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("all")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAll(@RequestParam(name="companyId") Long companyId){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(employeeService.getAll(companyId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-selected")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllEmpsSelected(
            @RequestParam(name="companyId") Long companyId,
            @RequestParam(name="limit") String l,
            @RequestParam(name="offset") String o,
            @RequestParam(name="search") String s
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .data(employeeService.getAllSelected(companyId,l,o,s))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> updateEmp(@RequestBody Employee employee){
        employeeService.updateEmp(employee);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @PermissionRequired(values = {"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> deleteEmpById(@RequestParam(name="id") Long id){
        employeeService.deleteEmp(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                //.status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
