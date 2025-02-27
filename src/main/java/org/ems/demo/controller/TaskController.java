package org.ems.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.dto.Task;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createTask(@Valid @RequestBody Task task){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(taskService.createTask(task))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllTask(
            @RequestParam(name="employeeId") Long employeeId,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="offset") int offset
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(taskService.getAllTask(employeeId,limit,offset))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updateTask(
            @Valid @RequestBody Task task
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(taskService.updateTask(task))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping()
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> deleteTask(
            @RequestParam(name="id") Long id
    ){
        taskService.deleteTask(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
