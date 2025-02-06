package org.ems.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;

    private String taskName;

    private Date startDate;

    private Date dueDate;

    private Date completedDate;

    private int overDues;

    private Employee employee;
}
