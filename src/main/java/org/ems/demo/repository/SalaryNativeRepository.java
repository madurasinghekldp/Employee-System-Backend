package org.ems.demo.repository;

import org.ems.demo.entity.SalaryEntity;

import java.util.List;

public interface SalaryNativeRepository {
    List<SalaryEntity> getAllSalaryByEmployee(Long employeeId, int limit, int offset);
}
