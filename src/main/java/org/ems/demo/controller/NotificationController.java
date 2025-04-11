package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/notifications")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user")
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN","ROLE_EMP"})
    public ResponseEntity<SuccessResponse> getAllNotifications(@RequestParam(name = "userId") Long userId) {
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(notificationService.getAllNotifications(userId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PutMapping()
    @PermissionRequired(values = {"ROLE_USER","ROLE_ADMIN","ROLE_EMP"})
    public ResponseEntity<SuccessResponse> markAsRead(@RequestParam(name = "notifyId") Long notifyId) {
        notificationService.markAsRead(notifyId);
        SuccessResponse successResponse = SuccessResponse.builder()
                .data("Notification marked as read")
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
