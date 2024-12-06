package org.ems.demo.repository;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.Department;
import org.ems.demo.entity.DepartmentEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface DepartmentNativeRepository {

    List<DepartmentEntity> getSelected(String l, String o, String s);
}
