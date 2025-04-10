package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = false)
    @JsonManagedReference(value = "role-employee")
    private List<EmployeeEntity> employees;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "company-role")
    private CompanyEntity company;
}
