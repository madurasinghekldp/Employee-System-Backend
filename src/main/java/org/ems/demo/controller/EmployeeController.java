package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Employee;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("emp")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private final EmployeeService employeeService;
    private String successStatus = "Success";

    @PostMapping
    public ResponseEntity<SuccessResponse> createEmp(@RequestBody Employee employee){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(employeeService.create(employee))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-selected")
    public ResponseEntity<SuccessResponse> getAllEmpsSelected(
            @RequestParam(name="limit") String l,
            @RequestParam(name="offset") String o
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(employeeService.getAllSelected(l,o))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> updateEmp(@RequestBody Employee employee){
        employeeService.updateEmp(employee);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> deleteEmpById(@RequestParam(name="id") Long id){
        employeeService.deleteEmp(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
