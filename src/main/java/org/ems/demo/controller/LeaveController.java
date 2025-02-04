package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Leave;
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
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> createLeave(@RequestBody Leave leave){
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

    @PutMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updateLeaves(
            @RequestBody Leave leave
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
}
