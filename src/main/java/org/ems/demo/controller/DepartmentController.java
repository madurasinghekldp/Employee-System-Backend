package org.ems.demo.controller;


import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Department;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dep")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    private final DepartmentService departmentService;
    private String successStatus = "Success";

    @PostMapping
    public ResponseEntity<SuccessResponse> createDep(@RequestBody Department department){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(departmentService.createDep(department))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getAllDep(){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(departmentService.getAll())
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-selected")
    public ResponseEntity<SuccessResponse> getAllDepsSelected(
            @RequestParam(name="limit") String l,
            @RequestParam(name="offset") String o,
            @RequestParam(name="search") String s
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .data(departmentService.getAllSelected(l,o,s))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> updateDep(@RequestBody Department department){
        departmentService.updateDep(department);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<SuccessResponse> deleteDepById(@RequestParam(name="id") Long id){
        departmentService.deleteDep(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(successStatus)
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
