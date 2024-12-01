package org.ems.demo.repository;

import org.ems.demo.entity.EmployeeEntity;
import java.util.List;

public interface EmployeeNativeRepository {
    List<EmployeeEntity> getSelected(String l, String o);
}
