package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String taskName;

    private Date startDate;

    private Date dueDate;

    private Date completedDate;

    private int overDues;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee-task")
    private EmployeeEntity employee;

    private String status;
    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    @JsonBackReference(value = "user-task")
    private UserEntity approvedBy;
}
