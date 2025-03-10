package org.ems.demo.service;

import org.ems.demo.dto.Employee;

import java.util.List;

public interface EmployeeService {
    Object create(Employee employee);

    Object getAllSelected(Long companyId, String l, String o, String s);

    void updateEmp(Employee employee);

    void deleteEmp(Long id);

    List<Employee> getAll(Long companyId, Long departmentId);

    Integer getCount(Long companyId);
}
