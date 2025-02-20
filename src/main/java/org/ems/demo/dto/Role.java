package org.ems.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private Long id;
    @Size(min=3,message = "Name must have at least 3 characters.")
    private String name;
    @Size(min=10,message = "Description must have at least 10 characters.")
    private String description;
    private Company company;
}
