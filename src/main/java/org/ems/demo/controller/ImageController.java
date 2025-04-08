package org.ems.demo.controller;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/profile/upload")
    @PermissionRequired(values = {"ROLE_USER", "ROLE_ADMIN", "ROLE_EMP"})
    public ResponseEntity<SuccessResponse> uploadProfile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .data(cloudinaryService.uploadProfile(file,userId))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    @PostMapping("/logo/upload")
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> uploadLogo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("companyId") Long companyId
    ) {

        SuccessResponse successResponse = SuccessResponse.builder()
                .data(cloudinaryService.uploadLogo(file,companyId))
                .build();
        return ResponseEntity.ok().body(successResponse);

    }
}

