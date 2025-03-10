package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Accessors(chain = true)
@Table(name="leaves")
public class LeaveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee-leave")
    private EmployeeEntity employee;

    private Date startDate;

    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    @JsonBackReference(value = "user-leave")
    private UserEntity approvedBy;

    private Integer dayCount;

}
