package org.ems.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskByUser {
    private Long id;
    @Size(min=10, message = "Task name must have at least 10 characters")
    private String taskName;
    @NotNull(message = "Provide start date")
    private Date startDate;
    @NotNull(message = "Provide due date")
    private Date dueDate;

    private Date completedDate;

    private int overDues;

    private Employee employee;
    @NotNull(message = "Status should be provided")
    private String status;
    private User approvedBy;
}
