package org.ems.demo.service;

import org.ems.demo.dto.Department;

import java.util.List;

public interface DepartmentService {
    Department createDep(Department department);

    List<Department> getAll(Long companyId);

    List<Department> getAllSelected(Long companyId, String l, String o, String s);

    void updateDep(Department department);

    void deleteDep(Long id);

    Integer getCount(Long companyId);
}
