package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString//(exclude = {"users", "departments", "roles", "employees"})
@Entity
@Accessors(chain = true)
@Table(name="company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;

    @Column(unique = true)
    private String registerNumber;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "company-department")
    private List<DepartmentEntity> departments;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "company-role")
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "company-user")
    private List<UserEntity> users;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "company-employee")
    private List<EmployeeEntity> employees;

    @Column(nullable = false)
    private Integer annualLeaves = 14;

    @Column(nullable = false)
    private Integer casualLeaves = 7;

    @Column(name = "logo")
    private String logo;

    @PrePersist
    public void prePersist() {
        if (annualLeaves == null) {
            annualLeaves = 14;
        }
        if (casualLeaves == null) {
            casualLeaves = 7;
        }
    }
}
