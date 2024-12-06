package org.ems.demo.service;

import org.ems.demo.dto.Employee;

public interface EmployeeService {
    Object create(Employee employee);

    Object getAllSelected(String l, String o, String s);

    void updateEmp(Employee employee);

    void deleteEmp(Long id);
}
