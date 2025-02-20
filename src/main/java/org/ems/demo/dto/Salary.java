package org.ems.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {
    private Long id;
    @Min(value = 0, message = "Payment should be positive value.")
    private Double payment;
    @NotNull(message = "Payment date should be provided.")
    private Date paymentDate;
    @NotNull(message = "Employee must be selected.")
    private Employee employee;
}
