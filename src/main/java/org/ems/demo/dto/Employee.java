package org.ems.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    @Size(min=3,message = "First name must have at least 3 characters.")
    private String firstName;
    @Size(min=3,message = "Last name must have at least 3 characters.")
    private String lastName;
    @Email(message = "Enter a valid email address.")
    private String email;
    @NotNull(message = "Department field should not null.")
    private Department department;
    @NotNull(message = "Role field should not null.")
    private Role role;
    private Company company;
}
