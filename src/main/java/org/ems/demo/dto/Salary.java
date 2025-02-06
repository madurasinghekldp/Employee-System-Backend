package org.ems.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {
    private Long id;
    private Double payment;
    private Date paymentDate;
    private Employee employee;
}
