package org.ems.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Leave;
import org.ems.demo.dto.LeaveByUser;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping()
    @PermissionRequired(values = {"ROLE_EMP"})
    public ResponseEntity<SuccessResponse> createLeave(@Valid @RequestBody LeaveByUser leave){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.createLeave(leave))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getAllLeaves(
            @RequestParam(name="employeeId") Long employeeId,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="offset") int offset
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.getAllLeaves(employeeId,limit,offset))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/all-by-user")
    @PermissionRequired(values = {"ROLE_EMP"})
    public ResponseEntity<SuccessResponse> getAllLeavesByUser(
            @RequestParam(name="userId") Integer userId,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="offset") int offset
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.getAllLeavesByUser(userId,limit,offset))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updateLeaves(
            @Valid @RequestBody Leave leave
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.updateLeave(leave))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping()
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> deleteLeaves(
            @RequestParam(name="id") Long id
    ){
        leaveService.deleteLeave(id);
        SuccessResponse successResponse = SuccessResponse.builder()
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/company-leaves")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> getLeaveCountsByDate(@RequestParam(name="companyId") Long companyId){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.getLeaveCounts(companyId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/user-leaves")
    @PermissionRequired(values = {"ROLE_EMP"})
    public ResponseEntity<SuccessResponse> getLeaveCountsByDateByUser(@RequestParam(name="userId") Integer userId){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.getLeaveCountsDatesByUser(userId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/count")
    @PermissionRequired(values = {"ROLE_EMP"})
    public ResponseEntity<SuccessResponse> getLeaveCountsByUser(@RequestParam(name="userId") Integer userId) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(leaveService.getLeaveCountsByUser(userId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
