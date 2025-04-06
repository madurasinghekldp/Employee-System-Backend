package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name="employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    @JsonBackReference(value = "department-employee")
    private DepartmentEntity department;

    @ManyToOne()
    @JoinColumn(name = "role_id",nullable = true)
    @JsonBackReference(value = "role-employee")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "company-employee")
    private CompanyEntity company;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "employee-salary")
    private List<SalaryEntity> salaries;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "employee-leave")
    private List<LeaveEntity> leaves;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "employee-task")
    private List<TaskEntity> tasks;

}
