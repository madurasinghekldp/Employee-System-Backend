package org.ems.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUser {
    @Email(message = "Please enter a valid email Id")
    @NotEmpty(message = "Must not be Empty and NULL")
    private String email;
    @Size(min=3,message = "First name must have at least 3 characters.")
    private String firstName;
    @Size(min=3,message = "Last name must have at least 3 characters.")
    private String lastName;
}
