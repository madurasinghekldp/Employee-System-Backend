package org.ems.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Company {
    private Long id;
    @Size(min=3,message = "Company name must contain at least 3 characters.")
    private String name;
    @Size(min=10,message = "Company address must contain at least 10 characters.")
    private String address;
    private String registerNumber;
    private Integer annualLeaves;
    private Integer casualLeaves;
    private String logo;
}
