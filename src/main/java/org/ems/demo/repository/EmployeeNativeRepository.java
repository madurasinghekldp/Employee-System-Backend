package org.ems.demo.repository;

import org.ems.demo.entity.EmployeeEntity;
import java.util.List;
import java.util.Map;

public interface EmployeeNativeRepository {
    List<EmployeeEntity> getSelected(Long companyId, String l, String o, String s);

    List<EmployeeEntity> getAll(Long companyId, Long departmentId);

    Integer getCount(Long companyId);

    Map<String,String> getById(Long id);
}
