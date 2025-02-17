package org.ems.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginUserDto {
    @Email(message = "Please enter a valid email Id")
    @NotEmpty(message = "Must not be Empty and NULL")
    private String email;

    private String password;
}
