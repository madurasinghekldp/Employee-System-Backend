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
    @Size(min=3,message = "First name must have at least 3 characters.")
    private String firstName;
    @Size(min=3,message = "Last name must have at least 3 characters.")
    private String lastName;
    @NotEmpty(message = "User role must be selected.")
    private String userRoleName;
    private Company company;
}
