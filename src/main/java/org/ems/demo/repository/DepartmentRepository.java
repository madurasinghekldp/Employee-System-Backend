package org.ems.demo.repository;

import org.ems.demo.entity.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity,Long> {
}
