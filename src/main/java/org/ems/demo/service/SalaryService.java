package org.ems.demo.service;

import org.ems.demo.dto.Salary;

import java.util.List;

public interface SalaryService {
    Salary createSalary(Salary salary);

    List<Salary> getAllSalary(Long employeeId, int limit, int offset);

    Salary updateSalary(Salary salary);

    void deleteSalary(Long id);
}
