package org.ems.demo.repository;

import org.ems.demo.entity.EmployeeEntity;
import java.util.List;

public interface EmployeeNativeRepository {
    List<EmployeeEntity> getSelected(Long companyId, String l, String o, String s);

    List<EmployeeEntity> getAll(Long companyId, Long departmentId);

    Integer getCount(Long companyId);
}
