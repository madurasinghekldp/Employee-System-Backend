package org.ems.demo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ems.demo.dto.Company;
import org.ems.demo.dto.RegisterUserDto;
import org.ems.demo.dto.SuccessResponse;
import org.ems.demo.security.PermissionRequired;
import org.ems.demo.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/company")
@RestController
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PutMapping
    @PermissionRequired(values = {"ROLE_ADMIN"})
    public ResponseEntity<SuccessResponse> updateCompany(
            @Valid @RequestBody Company company
    ){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(companyService.updateCompany(company))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }
}
