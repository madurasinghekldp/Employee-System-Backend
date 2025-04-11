package org.ems.demo.controller;

import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/notifications")
@RestController
public class NotificationController {

    @GetMapping("/user")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN","ROLE_EMP"})
    public ResponseEntity<SuccessResponse> getAllNotifications(@RequestParam(name = "userId") Long userId) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .data("All notifications")
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
