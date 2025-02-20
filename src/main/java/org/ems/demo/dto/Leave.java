package org.ems.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
    private Long id;
    @NotNull(message = "Employee must be selected.")
    private Employee employee;
    @NotNull(message = "Start date should be provided.")
    private Date startDate;
    @NotNull(message = "End date should be provided.")
    private Date endDate;
}
