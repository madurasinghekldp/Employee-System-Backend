package org.ems.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    @Email(message = "Please enter a valid email Id")
    @NotEmpty(message = "Must not be Empty and NULL")
    private String email;
    @Size(min=8,message = "Password should contain at least 8 characters.")
    private String password;
    private String firstName;
    private String lastName;
    private String userRoleName;
    private Company company;
}
