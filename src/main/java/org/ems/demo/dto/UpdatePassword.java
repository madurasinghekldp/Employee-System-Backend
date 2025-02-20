package org.ems.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePassword {
    private String oldPassword;
    @Size(min=8,message = "Password must contain at least 8 characters.")
    private String newPassword;
}
