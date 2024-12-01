package org.ems.demo.service;

import org.ems.demo.dto.Department;

import java.util.List;

public interface DepartmentService {
    Department createDep(Department department);

    List<Department> getAll();

    List<Department> getAllSelected(String l, String o);

    void updateDep(Department department);

    void deleteDep(Long id);
}
