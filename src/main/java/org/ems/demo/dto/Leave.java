package org.ems.demo.dto;

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
    private Employee employee;

    private Date startDate;

    private Date endDate;
}
