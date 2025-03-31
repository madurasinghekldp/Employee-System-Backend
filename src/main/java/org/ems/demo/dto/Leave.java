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
    @NotNull(message = "User should be provided.")

    private Date date;
    @NotNull(message = "Reason should be provided.")
    private String reason;
    @NotNull(message = "Leave type should be provided.")
    private LeaveType leaveType;
    @NotNull(message = "Day count should be provided.")
    private Double dayCount;
    private User approvedBy;
}
