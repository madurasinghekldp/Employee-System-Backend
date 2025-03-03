package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="department")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = false)
    @JsonManagedReference(value = "department-employee")
    private List<EmployeeEntity> employees;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "company-department")
    private CompanyEntity company;
}
