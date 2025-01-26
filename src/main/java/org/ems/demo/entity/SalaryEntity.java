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
@Table(name="salary")
public class SalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double payment;
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name="employee_id")
    @JsonBackReference(value = "employee-salary")
    private EmployeeEntity employee;
}
