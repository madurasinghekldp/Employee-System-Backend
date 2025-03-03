package org.ems.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveByUser {
    private Long id;
    @NotNull(message = "User should be provided.")
    private User user;
    @NotNull(message = "Start date should be provided.")
    private Date startDate;
    @NotNull(message = "End date should be provided.")
    private Date endDate;
    private User approvedBy;
}
